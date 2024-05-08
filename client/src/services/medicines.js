// Import necessary components and modules
import React, { useState, useEffect } from 'react';
import { Container, Button, Modal, ModalHeader, ModalBody, ModalFooter, Form, FormGroup, Label, Input, Alert, Table } from 'reactstrap';
import axios from '../api/axios';

const Medicines = () => {
  // State variables
  const [medicines, setMedicines] = useState([]);
  const [categories, setCategories] = useState([]);
  const [modal, setModal] = useState(false);
  const [formData, setFormData] = useState({ name: '', description: '', price: '', categoryId: '' });
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [cartId, setCartId] = useState(null); // State variable to store cartId

  // useEffect hook to fetch data on component mount
  useEffect(() => {
    fetchMedicines();
    fetchCategories();
    createCart(); // Call createCart function when component mounts
  }, []);

  // Function to fetch medicines from the server
  const fetchMedicines = async () => {
    setIsLoading(true);
    try {
      const response = await axios.get(`/api/medications/getAll`);
      setMedicines(response.data);
    } catch (error) {
      setErrorMessage('Error fetching medicines. Please try again later.');
    } finally {
      setIsLoading(false);
    }
  };

  // Function to fetch categories from the server
  const fetchCategories = async () => {
    try {
      const response = await axios.get(`/api/categories`);
      setCategories(response.data);
    } catch (error) {
      console.error('Error fetching categories:', error);
    }
  };

  // Function to create a new cart
  const createCart = async () => {
    try {
      const response = await axios.post(`/api/carts/createCart`);
      setCartId(response.data.cartId);
    } catch (error) {
      console.error('Error creating cart:', error);
    }
  };

  // Function to toggle modal
  const toggleModal = () => {
    setModal(!modal);
    if (!modal) {
      setFormData({ name: '', description: '', price: '', categoryId: '' });
      setSuccessMessage('');
      setErrorMessage('');
    }
  };

  // Function to handle adding medicine to cart
  const handleAddToCart = async (medicationId) => {
    try {
      setIsLoading(true);
      const medication = medicines.find(med => med.id === medicationId);
      const response = await axios.post(`/api/carts/${cartId}/items`, medication);
      if (response.status === 200) {
        setSuccessMessage('Medicine added to cart successfully!');
      } else {
        setErrorMessage('An error occurred while adding medicine to cart.');
      }
    } catch (error) {
      console.error('Error adding medicine to cart:', error);
      setErrorMessage('An error occurred while adding medicine to cart. Please try again later.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Container>
      <Button color="primary" onClick={toggleModal}>Add Medicine</Button>
      {isLoading && <p>Loading...</p>}
      {errorMessage && <Alert color="danger">{errorMessage}</Alert>}
      {successMessage && <Alert color="success">{successMessage}</Alert>}
      <Modal isOpen={modal} toggle={toggleModal}>
        <ModalHeader toggle={toggleModal}>Add Medicine</ModalHeader>
        <ModalBody>
          <Form>
            <FormGroup>
              <Label for="name">Name*</Label>
              <Input type="text" name="name" id="name" value={formData.name} onChange={(e) => setFormData({ ...formData, name: e.target.value })} />
            </FormGroup>
            <FormGroup>
              <Label for="description">Description*</Label>
              <Input type="text" name="description" id="description" value={formData.description} onChange={(e) => setFormData({ ...formData, description: e.target.value })} />
            </FormGroup>
            <FormGroup>
              <Label for="price">Price*</Label>
              <Input type="number" name="price" id="price" value={formData.price} onChange={(e) => setFormData({ ...formData, price: e.target.value })} />
            </FormGroup>
            <FormGroup>
              <Label for="categoryId">Category*</Label>
              <Input type="select" name="categoryId" id="categoryId" value={formData.categoryId} onChange={(e) => setFormData({ ...formData, categoryId: e.target.value })}>
                <option value="">Select category</option>
                {categories.map(category => (
                  <option key={category.id} value={category.id}>{category.name}</option>
                ))}
              </Input>
            </FormGroup>
          </Form>
        </ModalBody>
        <ModalFooter>
          <Button color="primary" onClick={handleAddToCart}>Add</Button>{' '}
          <Button color="secondary" onClick={toggleModal}>Cancel</Button>
        </ModalFooter>

      </Modal>

      <Table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Category</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {medicines.map((medicine) => (
            <tr key={medicine.id}>
              <td>{medicine.name}</td>
              <td>{medicine.description}</td>
              <td>${medicine.price}</td>
              <td>{categories.find(category => category.id === medicine.categoryId)?.name}</td>
              <td className="text-right">
                <Button color="success" onClick={() => handleAddToCart(medicine.id)}>Add to Cart</Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </Container>
  );
};

export default Medicines;
