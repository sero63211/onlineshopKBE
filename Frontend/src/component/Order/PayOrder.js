import React from 'react'
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

import axios from 'axios';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import { url } from '../../ServiceApi/ServiceApi';



export default function PayOrder({orderDetails}) {
 
    const navigate = useNavigate();
    
    const [open, setOpen] = React.useState(false); 
    const [message, setmessage] = useState('');


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
      
        axios.post(url+'order/user', orderDetails
       // , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
       , {}) 
       .then(res => {
          
          console.log(res.data);
          setOpen(true);
          setmessage('your order placed successfully...')
           
           setTimeout(function() {
            navigate('/myorders');
        }, 1000);
         
          
        })
        .catch(e => {
    
          console.log(e.response.data.message);
           setmessage(e.response.data.message);
        })
    
   

  return (
    <div>
        <Snackbar
        open={open}
        autoHideDuration={6000}
        onClose={handleClose}
        message={message}
        anchorOrigin={{
          vertical: "top",
          horizontal: "right"
       }}
        action={action}
      />
      
    </div>
  )
}
 
