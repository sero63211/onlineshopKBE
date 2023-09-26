import React from 'react'
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useState, useEffect } from 'react';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import GridItem from './GridItem';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import '../Cart/CSS/Shopping.css'
import Navbar from '../Navbar/Navbar';
import { url } from '../../ServiceApi/ServiceApi';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import { CircularProgress, FormControl, InputLabel, MenuItem, Select, Typography } from '@mui/material';
import DuitnowImg from './images/Duitnow.png';
import GrabpayImg from './images/Grabpay.png';
import { PayPalButton } from 'react-paypal-button-v2';


function AddToCartIcon({ cartCount1 }) {

  if (!cartCount1) {
    cartCount1 = 0;
  }

  const [orderOpen, orderSetOpen] = React.useState(false);



  const handlePaymentDialogClose = () => {
    orderSetOpen(false);
  };








  const navigate = useNavigate();
  const [open, setOpen] = React.useState(false);
  const [cartCount, setCartCount] = React.useState(cartCount1);

  const handleClose = (event, reason) => {
    //navigate('/myorders');
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
  const data = {
    "userId": localStorage.getItem("userId"),
    "orderStatus": "ACTIVE"
  };
  const [cartInfo, setCartInfo] = useState([]);
  const [cartid, setCartId] = useState(0);
  const [load, setLoad] = useState(false);
  useEffect(() => {
    handleGetCartItem();
  }, []);
  async function handleGetCartItem() {
    try {
      setLoad(true);
      await axios.post(url + 'order/cart/data', data
        //, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
        , {})
        .then(res => {
          setLoad(false);
          if (res.data) {
            console.log(res.data);
            setCartId(res.data.cartId)
            setCartInfo(res.data.item);
            console.log(res.data);
            console.log(res.data.cartId);
            axios.post(url + 'order/cart/data', data
              //, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
              , {})
              .then(res => {
                {
                  setCartCount(res.data.item.length);
                }
              })
              .catch(e => {
                console.log(e.message);
              });
          }

        })
    } catch (error) {
      console.error(error);
      if (!cartInfo) {
        return navigate('/not-found');
      }
    }
  }
  const totalAmount = [];
  {
    cartInfo.map((e) => (
      totalAmount.push(e.totalPrice)
    ))
  }
  let sum = 0;
  for (let i = 0; i < totalAmount.length; i++) {
    sum += totalAmount[i];
  }
  const [message, setmessage] = useState('');
  const [payButton, setPayButton] = useState(false);

  const date = new Date();

  const [paymentOption, setPaymentOption] = React.useState("");
  const [referenceNumber, setReferenceNumber] = React.useState("");

  const handlePaymentOption = (e) => {
    setPaymentOption(e.target.value);
    if (e.target.value !== "") {
      setReferenceNumber(Math.floor(100000000 + Math.random() * 900000000));
    }
  }

  const onSuccess = (details, data) => {
    console.log("--------PAYPAL START-----------");
    console.log(details);
    console.log(data);
    console.log("--------PAYPAL END-----------");
    const orderDetails = {
      "transactionId": data.paymentID,
      "paymentOption": data.paymentSource,
      "payerId": data.payerID,
      "paymentStatus": details.status,
      "cartId": cartid,
      "userId": localStorage.getItem("userId")
    };

    axios.post(url + 'order', orderDetails
      //, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      , {})
      .then(res => {
        console.log(res.data);
        navigate('/myorders');
        setOpen(true);
        setmessage('your order placed successfully...Please check the status in My orders tab')
        setCartId(0);
        orderSetOpen(false);
        
      })
      .catch(e => {
        console.log(e);
        //setmessage(e.response.data.message);
      })
  };
  const submitMyOrders = () => {

    if (cartid == 0) {
      setmessage('Cart is empty You Add to some item your cart');
      setOpen(true);
      return;
    } else {
    }
    if (!paymentOption || !referenceNumber) {
      setmessage('Fields cannot be blank')
      setOpen(true);
    } else {
      const orderDetails = {
        "referenceNumber": referenceNumber,
        "paymentOption": paymentOption,
        "cartId": cartid,
        "userId": localStorage.getItem("userId"),
        "date": date
      };

    }



  };




  return (
    <div>
      <Navbar itemCount2={cartCount} />
      <br />
      <br />
      <br />
      {payButton ? (
        ""
      ) : (
        <>
        </>
      )
      }
      <Grid container spacing={2}>
        <Grid item xs={8} style={{ marginTop: 13, backgroundColor: '#ffffff' }}>
          <h2><b>Shopping Cart</b></h2>
          <Grid container spacing={2}  >
            {
              load ? <CircularProgress /> : ""
            }

            {cartInfo.map((e) => (
              <Grid item xs={12}>
                <GridItem handleGetCartItem={handleGetCartItem} productImage={e.product.imageData} productId={e.product.productId} productName={e.product.productName} stock={e.product.stocks}
                  quantity={e.quantity} price={e.product.price} totalprice={e.totalPrice} itemid={e.itemId}
                  cartId={cartid} />
              </Grid>
            ))}
          </Grid>
        </Grid>
        <Grid item xs={4}>
          <div style={{ marginTop: 50, overflow: 'auto' }}>
            <p style={{}}><strong>Total:</strong> &nbsp; <span><b>$</b></span>&nbsp;<b>{sum}</b></p>

            {
              sum !== 0 ? (
                <p style={{ color: 'green', paddingRight: 10 }}>Your order is eligible to check out</p>
              ) : (
                <p style={{ color: 'green', paddingRight: 10 }}>Not items in cart, Your order is not eligible to check out</p>
              )
            }

            {/* <Button onClick={submitMyOrders} 
            disabled={sum===0}
            style={{ borderRadius: 20, backgroundColor: '#f0de16', color: '#000000' }}>
              <span style={{ fontWeight: 500, fontSize: 14 }}><b>Proceed to Buy</b></span>

            
            </Button> */}
            {
              sum !== 0 ? (
                <PayPalButton
                  amount={sum}
                  currency="USD"
                  onSuccess={onSuccess}
                  options={{
                    clientId: "AeDKUs80SQ7WBsUtwi1uxhTlg4J4TqJVIaODkQCl2IAhYvVzHrVY1oGacdHB6U3kTBKPmIZN9-SFQUTU",
                  }} />
              ) : ""}
          </div>
        </Grid>
      </Grid>

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
  )
}
export default AddToCartIcon
