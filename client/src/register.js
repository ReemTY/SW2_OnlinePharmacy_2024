import React, { useState } from 'react';
import { Row, Col, Card, CardTitle, Button } from 'reactstrap';
import { Link, useNavigate } from 'react-router-dom';
import axios from './api/axios';

const REGISTER_URL = '/api/signup'; // API URL

const Registration = () => {
    const [formData, setFormData] = useState({
        name: '',
        username: '',
        email: '',
        password: '',
        address: ''
    });
    const [nameError, setNameError] = useState('');
    const [usernameError, setUsernameError] = useState('');
    const [emailError, setEmailError] = useState('');
    const [passwordError, setPasswordError] = useState('');
    const [addressError, setAddressError] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const { name, username, email, password, address } = formData;

        setNameError('');
        setUsernameError('');
        setEmailError('');
        setPasswordError('');
        setAddressError('');

        if (name.trim() === '') {
            setNameError('Please enter your name');
            return;
        }

        if (username.trim() === '') {
            setUsernameError('Please enter your username');
            return;
        }

        if (email.trim() === '') {
            setEmailError('Please enter your email');
            return;
        }

        if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email)) {
            setEmailError('Please enter a valid email');
            return;
        }

        if (password.trim() === '') {
            setPasswordError('Please enter a password');
            return;
        }
        if (address.trim() === '') {
                    setAddressError('Please enter your address');
                    return;
                }

        try {
            const response = await axios.post(REGISTER_URL, {
                name,
                username,
                email,
                password,
                address
            });

            if (response.status === 200) {
                // Registration successful, redirect to login page
                navigate('/login');
            } else {
                // Registration failed, display error message
                setPasswordError(response.data.message);
            }
        } catch (error) {
            console.error('Error occurred:', error);
        }
    };

    return (
        <Row className="justify-content-center">
            <Col lg="6">
                <Card className="mt-5">
                    <CardTitle tag="h6" className="border-bottom p-3 mb-0 text-center">
                        <i className="bi bi-person-plus me-2"></i> Register
                    </CardTitle>
                    <div className="p-4">
                        <form onSubmit={handleSubmit}>
                            <div className="mb-3">
                                <input
                                    type="text"
                                    name="name"
                                    className="form-control"
                                    placeholder="Name"
                                    value={formData.name}
                                    onChange={handleChange}
                                />
                                <label className="errorLabel">{nameError}</label>
                            </div>
                            <div className="mb-3">
                                <input
                                    type="text"
                                    name="username"
                                    className="form-control"
                                    placeholder="Username"
                                    value={formData.username}
                                    onChange={handleChange}
                                />
                                <label className="errorLabel">{usernameError}</label>
                            </div>
                            <div className="mb-3">
                                <input
                                    type="email"
                                    name="email"
                                    className="form-control"
                                    placeholder="Email"
                                    value={formData.email}
                                    onChange={handleChange}
                                />
                                <label className="errorLabel">{emailError}</label>
                            </div>
                            <div className="mb-3">
                                <input
                                    type="password"
                                    name="password"
                                    className="form-control"
                                    placeholder="Password"
                                    value={formData.password}
                                    onChange={handleChange}
                                />
                                <label className="errorLabel">{passwordError}</label>
                            </div>
                            <div className="mb-3">
                                <input
                                    type="text"
                                    name="address"
                                    className="form-control"
                                    placeholder="Address"
                                    value={formData.address}
                                    onChange={handleChange}
                                />
                            </div>
                            <Button type="submit" color="primary" block>Register</Button>
                        </form>
                        <p className="mt-3 mb-0 text-center">
                            Already have an account? <Link to="/login">Login</Link>
                        </p>
                    </div>
                </Card>
            </Col>
        </Row>
    );
};

export default Registration;