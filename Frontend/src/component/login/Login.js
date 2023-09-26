import CloseIcon from '@mui/icons-material/Close';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import Snackbar from '@mui/material/Snackbar';
import {
  MDBCard,
  MDBCardBody,
  MDBCol,
  MDBContainer,
  MDBRow,
} from 'mdb-react-ui-kit';
import * as React from 'react';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Shopping from '../../images/logo_secure_cart.png';
import TextField from '@mui/material/TextField';
import { loginUser } from '../../ServiceApi/ServiceApi';
import './Login.css';
export default function Login() {
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
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')


  const handleEmail = (e) => {
    setEmail(e.target.value)
  }
  const handlePassword = (e) => {
    setPassword(e.target.value)
  }
  const handlesignup = (e) => {
    navigate('/register');
  }
  //const emailPattern = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/;
  //const passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[a-zA-Z]).{8,}$/;
  const [message, setmessage] = useState('');
  const handleApi = () => {

    if (!email || !password) {
      setmessage('Fields cannot be blank!');
      setOpen(true);
      return;
    }
    // if (!emailPattern.test(email)) {
    //   setmessage('Please enter a valid email address');
    //   setOpen(true);
    //   return;
    // }
    // if (!passwordPattern.test(password)) {
    //   setmessage('Password must be at least 8 characters long and contain  one lowercase letter, and one number');
    //   setOpen(true);
    //   return;
    // }


    loginUser(email, password).then(result => {
      setOpen(true);
      setmessage('login successfully');
      localStorage.setItem('Token', result.data.token);
      localStorage.setItem('username', result.data.firstName + " " + result.data.lastName);
      localStorage.setItem('email', result.data.email);
      localStorage.setItem('roles', result.data.roles);
      localStorage.setItem('userId', result.data.id);
      navigate('/home');
      // if (localStorage.getItem('Token') !== "" && result.data.status==="APPROVED") {
      //   navigate('/home');
      // } else if (localStorage.getItem('Token') !== "" && result.data.status==="PENDING") {
      //   navigate('/noaccess');
      // }
      // else {
      //   return navigate('/');
      // }
    })
      .catch(error => {
        console.log(error);
        setOpen(true);
        setmessage('Incorrect Email or Password');
        setEmail('')
        setPassword('')
        console.log(error)
      })
  }




  return (
    <div className="App" style={{height:'100%'}}>
      <MDBContainer style={{ backgroundColor: 'aliceblue', minHeight:'calc(100vh - 1px)' }}>
        <MDBRow className='d-flex justify-content-center align-items-center h-100'>
          <MDBCol col='12'>
            <MDBCard className='my-2 mx-auto t-3' style={{ borderRadius: '1rem', top:'30px', maxWidth: '500px' }}>
              <MDBCardBody className='w-100 d-flex flex-column' >
                <div style={{ textAlign: 'center', padding: '0px 30px 0px 30px' }}>
                  <img style={{ height: 130, width: 130, cursor: 'pointer' }} className='text-center' src={Shopping} />
                  <h5 className="fw-bold text-center">Sign in here</h5>
                  {/* <label class="form-label" for="typeEmailX-2">Email</label>
        <input value={email} onChange={handleEmail} type="email" id="typeEmailX-2" class="form-control form-control-lg" /> */}
                  <TextField
                    style={{ marginTop: 20 }}
                    fullWidth
                    label="Email"
                    size='small'
                    value={email}
                    onChange={handleEmail}
                  />

                  <TextField fullWidth 
                      style={{ marginTop: 25, marginBottom: 20 }} 
                      size='small' type={'password'} 
                      label={'Password'} 
                      id="margin-dense" margin="dense" 
                      value={password} 
                      onChange={handlePassword} 
                    />


                  <Button variant="contained" size='small' style={{ width: '60%' }} onClick={handleApi} >Login</Button>

                  <p sx={{ marginTop: 2 }}> Don't have a account? <a style={{ color: 'blue', cursor: 'pointer', textDecoration: 'none' }} onClick={handlesignup}>Register</a>
                  </p>

                </div>


              </MDBCardBody>
            </MDBCard>
          </MDBCol>
        </MDBRow>
      </MDBContainer>
      <Snackbar
        open={open}
        autoHideDuration={6000}
        onClose={handleClose}
        message={message}
        anchorOrigin={{
          vertical: "top",
          horizontal: "center"
        }}
        action={action}
      />

    </div>
  );
}