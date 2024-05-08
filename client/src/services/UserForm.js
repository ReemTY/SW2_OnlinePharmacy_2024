import React, { useState, useEffect } from 'react';
import { Button, Form, FormGroup, Label, Input } from 'reactstrap';
import axios from '../api/axios';

const USER_URL = '/api/users';

const UserForm = ({ userId }) => {
  const [formData, setFormData] = useState({ username: '', email: '', password: '', name: '', address: '' });
  const [user, setUser] = useState(null);

  useEffect(() => {
    if (userId) {
      fetchUser();
    }
  }, [userId]);

  const fetchUser = async () => {
    try {
      const response = await axios.get(`${USER_URL}/${userId}`); // Fetch a specific user by ID
      setUser(response.data);
    } catch (error) {
      console.error('Error fetching user details:', error);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleUpdate = async () => {
    try {
      await axios.put(`${USER_URL}/${userId}`, formData); // Update user by ID
      alert('User updated successfully!');
    } catch (error) {
      console.error('Error updating user:', error);
    }
  };

  const handleDelete = async () => {
    if (window.confirm('Are you sure you want to delete this user?')) {
      try {
        await axios.delete(`${USER_URL}/${userId}`); // Delete user by ID
        alert('User deleted successfully!');
        setUser(null);
      } catch (error) {
        console.error('Error deleting user:', error);
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post(`${USER_URL}/createUser`, formData); // Create a new user
      alert('User created successfully!');
      setFormData({ username: '', email: '', password: '', name: '', address: '' });
    } catch (error) {
      console.error('Error creating user:', error);
    }
  };

  return (
    <div>
      <h2>{userId ? 'Edit User' : 'Create User'}</h2>
      <Form onSubmit={handleSubmit}>
        <FormGroup>
          <Label for="username">Username</Label>
          <Input type="text" name="username" id="username" value={formData.username} onChange={handleChange} />
        </FormGroup>
        <FormGroup>
          <Label for="email">Email</Label>
          <Input type="email" name="email" id="email" value={formData.email} onChange={handleChange} />
        </FormGroup>
        <FormGroup>
          <Label for="password">Password</Label>
          <Input type="password" name="password" id="password" value={formData.password} onChange={handleChange} />
        </FormGroup>
        <FormGroup>
          <Label for="name">Name</Label>
          <Input type="text" name="name" id="name" value={formData.name} onChange={handleChange} />
        </FormGroup>
        <FormGroup>
          <Label for="address">Address</Label>
          <Input type="text" name="address" id="address" value={formData.address} onChange={handleChange} />
        </FormGroup>
        <Button color="primary" type="submit">{userId ? 'Update' : 'Create'}</Button>
      </Form>

      {userId && (
        <div>
          <h2>Delete User</h2>
          <Button color="danger" onClick={handleDelete}>Delete</Button>
        </div>
      )}

      {userId && user && (
        <div>
          <h2>User Details</h2>
          <p>Name: {user.username}</p>
          <p>Email: {user.email}</p>
          <p>Name: {user.name}</p>
          <p>Address: {user.address}</p>
        </div>
      )}
    </div>
  );
};

export default UserForm;
