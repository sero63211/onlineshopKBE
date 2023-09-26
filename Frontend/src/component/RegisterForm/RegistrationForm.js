import Box from '@mui/material/Box';
import * as React from 'react';
import Shopping from '../../images/logo_secure_cart.png';
import { useState } from "react";
import FormControl from '@mui/material/FormControl';
import { useNavigate } from 'react-router-dom';
import TextField from '@mui/material/TextField';
import axios from "axios";
import { url } from "../../ServiceApi/ServiceApi";
import {
  MDBBtn,
  MDBContainer,
  MDBCard,
  MDBCardBody,
  MDBCol,
  MDBRow,
}
  from 'mdb-react-ui-kit';
import Button from '@mui/material/Button';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import { InputLabel, MenuItem, Select } from '@mui/material';
function RegistrationForm() {
  const navigate = useNavigate();
  const [open, setOpen] = React.useState(false);
 
  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
  };
  const action = (
    <React.Fragment>
      <IconButton
        size="small"
        aria-label="close"
        color="inherit"
        onClick={handleClose}
      >
        <CloseIcon fontSize="small" />
      </IconButton>
    </React.Fragment>
  );
  const [fname, setfName] = useState('')
  const [lName, setlName] = useState('')
  const [role, setRole] = useState('')
  const [email, setEmail] = useState('')
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [mobile, setMobile] = useState('')
  function clearData() {
    setfName('');
    setlName('');
    setEmail('');
    setPassword('');
    setMobile('');
    setUsername('');
  }
  const handleFname = (e) => {
    setfName(e.target.value)
  }
 
  const handlelName = (e) => {
    setlName(e.target.value)
  }
  const handleRole = (e) => {
    setRole(e.target.value)
  }
  const handleEmail = (e) => {
    setEmail(e.target.value)
  }
  const handleMobile = (e) => {
    setMobile(e.target.value)
  }
  const handleUsername = (e) => {
    setUsername(e.target.value)
  }
  
  const handlePassword = (e) => {
    setPassword(e.target.value)
  }
  const emailPattern = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/;
  const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%?=*&]).{8,}$/;
  const [message, setmessage] = useState('');
  const handleApi = () => {
    setOpen(true);

    

    if (!fname) {
      setmessage('Please enter your first name.')
      //clearData();
      return;
    }
    if (!lName) {
      setmessage('Please enter your last name.')
      //clearData();
      return;
    }

    if (!username) {
      setmessage('Please enter Username');
      //clearData();
      return;
    }
    
    if (!emailPattern.test(email)) {
      setmessage('Please enter a valid email address');
      //clearData();
      return;
    }
    if (!password) {
      setmessage('Please enter your password')
      //clearData();
      return;
    }
    if (!mobile) {
      setmessage('Please enter valid Phone number');
      //clearData();
      return;
    }
    if (!role) {
      setmessage('Please choose any role')
      //clearData();
      return;
    }
    
    
   

    
    // if (!passwordPattern.test(password)) {
    //   setmessage('Password must be at least 8 characters, contain atleast one Uppercase letter and special character');
    //   //clearData();
    //   return;
    // }

    setOpen(false);
    
    let data = JSON.stringify({
      name: fname,
      surname: lName,
      email: email,
      password: password,
      username: username,
      role: role,
      phoneNumber: mobile,
    })

    alert(data);
    axios.post(url+'user/signup', data, {
      headers: {
        "Content-Type": "application/json"
      }
    }).then(result => {
      setmessage('Registered successfully');
      console.log(result.data)
      setTimeout(function () {
        navigate('/');
      }, 2000);
    })
      .catch(error => {
        console.log(error)
        setmessage(error.response.data.message);
        clearData();
      })
  }
  const handlesignin = () => {
    navigate('/')
  }
  return (
    <div>
      <MDBContainer  style={{backgroundColor:'aliceblue', minHeight:'calc(100vh - 10px)'}}>
        <MDBRow>
          <MDBCol md='2'></MDBCol>
          <MDBCol md='8'>
          <MDBCard className='my-2 ' style={{ borderRadius: '1rem', textAlign: 'center', top:'10px'}}>
              <MDBCardBody style={{ textAlign: 'center', }}>
                <img style={{ height: 130, width: 130, cursor: 'pointer' }} src={Shopping} />
                <h5 className="fw-bold mb-2 text-center">Create your Account</h5>
                <MDBRow>
                  <MDBCol col='12'>
                    <TextField fullWidth size='small' value={fname} onChange={handleFname} label={'First Name'} id="margin-dense" margin="dense" required />
                    <TextField fullWidth size='small' value={lName} onChange={handlelName} label={'Last Name'} id="margin-dense" margin="dense" required />
                    <TextField fullWidth size='small' value={username} onChange={handleUsername} label={'Username'} id="margin-dense" margin="dense" required />
                    <TextField fullWidth size='small' value={password} onChange={handlePassword} label={'Password'} id="margin-dense" type={'password'} margin="dense" required />
                  </MDBCol>
                  <MDBCol col='12' className='mb-3'>
                  <TextField fullWidth size='small' value={email} onChange={handleEmail} label={'Email'} id="margin-dense" margin="dense" required />
                    
                    <TextField fullWidth size='small' value={mobile} onChange={handleMobile} label={'Phone number'} id="margin-dense" margin="dense" required />
                    
                    <FormControl fullWidth size='small'  margin="dense">
                    <InputLabel id="demo-simple-select-label">Are you a </InputLabel>
                    <Select
                      labelId="demo-simple-select-label"
                      id="demo-simple-select"
                      value={role}
                      size='small'
                      label="Are you a"
                      onChange={handleRole}
                    >
                      {/* <MenuItem value={"admin"}>ADMIN</MenuItem> */}
                      <MenuItem value={"seller"}>SELLER</MenuItem>
                      <MenuItem value={"user"}>BUYER</MenuItem>
                    </Select>
                  </FormControl>
                  </MDBCol> 
                  
                </MDBRow>
                
                
                <FormControl required fullWidth sx={{ m: 1, minWidth: 150 }} size="medium">
                </FormControl>
                <Box >
                  <div style={{ display: 'flex', justifyContent: 'center' }}>
                    <Button variant="contained" size='small' style={{width:'70%'}} fullWidth onClick={handleApi} >Sign Up</Button>
                  </div>
                  <p>Already have an account?  <a style={{ color: 'blue', cursor: 'pointer', textDecoration: 'none' }} onClick={handlesignin}>Login</a>
                  </p>
                </Box>
              </MDBCardBody>
            </MDBCard>
          </MDBCol> <MDBCol md='2'></MDBCol>
        </MDBRow>
      </MDBContainer>
      <Snackbar
        open={open}
        autoHideDuration={6000}
        anchorOrigin={{
          vertical: "top",
          horizontal: "center"
        }}
        onClose={handleClose}
        message={message}
        action={action}
      />
    </div>
  );
}
export default RegistrationForm;
