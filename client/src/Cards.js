import React, { useState, useEffect } from "react";
import {
  Table,
  Row,
  Col,
  Button,
  Input,
  FormGroup,
  Label,
} from "reactstrap";

import axios from '../../api/axios';

const MED_URL = '/api/medications'; //api url
const CAT_URL = '/api/categories'; //api url

const Cards = () => {
  const [medicines, setMedicines] = useState([]);
  const [categories, setCategories] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("");
  const [error, setError] = useState(null);
  const [filteredMedicinesByCategory, setFilteredMedicinesByCategory] = useState([]);
  const [userId, setUserId] = useState(null); // State to hold the user ID

  useEffect(() => {
    // Fetch categories from API
    fetchCategories();
  }, []);

  const fetchMedicines = async () => {
    try {
      const response = await axios.get(`${MED_URL}/name/${searchTerm}`);
      setMedicines(response.data);
      setError('');
    } catch (error) {
      console.error('Error fetching medicines:', error);
      setError('Failed to fetch medicines. Please try again.');
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await axios.get(`${CAT_URL}/getAll`);
      setCategories(response.data);
    } catch (error) {
      console.error('Error fetching categories:', error);
      setError('Failed to fetch categories. Please try again.');
    }
  };

  const handleSearch = () => {
    fetchMedicines();
  };

  const handleCategoryChange = (event) => {
    setSelectedCategory(event.target.value);
  };

  const addToCart = (medicine) => {
    // Implement logic to add medicine to cart
    console.log('Added to cart:', medicine);
  };

  return (
    <div>
      <Row>
        <Col md={6}>
          <Input
            type="text"
            placeholder="Search for medicines..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </Col>
        <Col md={6}>
          <FormGroup>
            <Label htmlFor="categorySelect">Filter by Category:</Label>
            <select
              id="categorySelect"
              value={selectedCategory}
              onChange={handleCategoryChange}
            >
              <option value="">All Categories</option>
              {categories.map((category) => (
                <option key={category.id} value={category.id}>
                  {category.name}
                </option>
              ))}
            </select>
          </FormGroup>
        </Col>
      </Row>
      <Row>
        <Col md={6}>
          <Button color="primary" onClick={handleSearch}>Search</Button>
        </Col>
      </Row>
      {error && <div className="error">{error}</div>}
      <Table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Category</th>
            <th>Description</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {medicines.map((medicine, index) => (
            <tr key={index}>
              <td>{medicine.name}</td>
              <td>{medicine.category}</td>
              <td>{medicine.description}</td>
              <td>
                <Button color="primary" onClick={() => addToCart(medicine)}>Add to Cart</Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
};

export default Cards;
