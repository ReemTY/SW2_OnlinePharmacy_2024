import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button, Modal, ModalHeader, ModalBody, ModalFooter, Form, FormGroup, Label, Input, Alert, Spinner, Table } from 'reactstrap';
import axios from '../api/axios';
import FullLayout from '../layouts/FullLayout';

const CAT_URL = '/api/categories'; // API URL

function Categories() {
  const [categories, setCategories] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const [formData, setFormData] = useState({
    categoryName: '',
    medications: []
  });

  const [modal, setModal] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState(null);

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    setIsLoading(true);
    try {
      const response = await axios.get(`${CAT_URL}/getAll`);
      setCategories(response.data || []); // Ensure categories is initialized as an array
    } catch (error) {
      setError('Error fetching categories. Please try again later.');
    } finally {
      setIsLoading(false);
    }
  };

  const createCategory = async () => {
    try {
      const response = await axios.post(`${CAT_URL}/addCategory`, formData);
      console.log('Response after adding category:', response.data); // Log the response
      setModal(false);
      fetchCategories(); // Refresh categories after creation
    } catch (error) {
      console.error('Error creating category:', error);
      setError('Error creating category. Please try again.');
    }
  };


  const deleteCategory = async (categoryId) => {
    try {
      await axios.delete(`${CAT_URL}/delete/${categoryId}`);
      fetchCategories(); // Refresh categories after deletion
    } catch (error) {
      console.error('Error deleting category:', error);
      setError('Error deleting category. Please try again.');
    }
  };

  const updateCategory = async () => {
    try {
      await axios.put(`${CAT_URL}/update/${selectedCategory.categoryId}`, formData);
      setModal(false);
      fetchCategories(); // Refresh categories after update
    } catch (error) {
      console.error('Error updating category:', error);
      setError('Error updating category. Please try again.');
    }
  };

  const toggleModal = () => {
    setModal(!modal);
    setSelectedCategory(null);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const editCategory = (category) => {
    setFormData(category);
    setSelectedCategory(category);
    setModal(true);
  };

  return (
    <div>
      <Container>
        <Row>
          <Col>
            <h1>Categories</h1>
            <Button color="primary" onClick={toggleModal} style={{ marginBottom: '20px' }}>+</Button>
            {isLoading && <Spinner color="primary" />}
            {error && (
              <Alert color="danger">
                {error} <Button color="link" onClick={fetchCategories}>Retry</Button>
              </Alert>
            )}
            {!isLoading && !error && (
              <Table>
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {categories.map((category) => (
                    <tr key={category.categoryId}>
                      <td>{category.categoryName}</td>
                      <td>
                        <Button color="danger" onClick={() => deleteCategory(category.categoryId)}>Delete</Button>{' '}
                        <Button color="primary" onClick={() => editCategory(category)}>Update</Button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            )}
          </Col>
        </Row>
      </Container>
      <Modal isOpen={modal} toggle={toggleModal}>
        <ModalHeader toggle={toggleModal}>{selectedCategory ? 'Edit Category' : 'Add Category'}</ModalHeader>
        <ModalBody>
          <Form>
            <FormGroup>
              <Label for="categoryName">Category Name</Label>
              <Input type="text" name="categoryName" id="categoryName" value={formData.categoryName} onChange={handleInputChange} style={{ marginBottom: '10px' }} />
            </FormGroup>
          </Form>
        </ModalBody>
        <ModalFooter>
          <Button color="primary" onClick={selectedCategory ? updateCategory : createCategory}>
            {selectedCategory ? 'Update' : 'Create'}
          </Button>{' '}
          <Button color="secondary" onClick={toggleModal}>Cancel</Button>
        </ModalFooter>
      </Modal>
    </div>
  );
}

export default Categories;
