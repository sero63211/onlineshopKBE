import React, { useState } from 'react'
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import axios from 'axios';
import deletecart from './images/icons8-ecommerce-64.png'
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import { url } from '../../ServiceApi/ServiceApi';
const GridItem = ({handleGetCartItem, productImage, productId, productName, quantity, stock, price, totalprice, itemid, cartId }) => {
  const[selectQuantity, setSelectQuantity] = useState(quantity);
  const handleDeleteItem = () => {
    axios.delete(url+'order/items/'+itemid+'/'+cartId+'/'+ localStorage.getItem('userId')
    //, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
    , {})
    .then(res => {
      console.log(res.data);
      handleGetCartItem();
    })
    .catch(e => {
      console.log(e.message);
    })
  }
  const[message, setMessage] = useState();
  const handleSelectChange = (event) => {
    setSelectQuantity(event.target.value);
    if(stock <= event.target.value){
      setOpen(true);
      setMessage("can't add more quantity. Beacause only "+stock+" is left");
   } 
    const cartData = {
        "quantity": event.target.value,
			  "productId": productId,
				"userId": localStorage.getItem("userId"),
        "cartId":cartId  
    };
   axios.put(url+'order/items/'+itemid,cartData
  //  , {
  //     headers: {
  //       Authorization: `Bearer ${localStorage.getItem('Token')}`
  //     }
  //   })
    , {})
      .then((response) => {
        setSelectQuantity(event.target.value);
        handleGetCartItem();
        console.log(response.data);
      })
      .catch((error) => {
       console.log(error);
      });
  }
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
  return (
    <div>
             <Snackbar
        open={open}
        autoHideDuration={6000}
        onClose={handleClose}
        message={message}
        action={action}
        anchorOrigin={{
          vertical: "top",
          horizontal: "center"
       }}
      />
<div>
      <div className="shopping-cart">
        <div className="column-labels">
                 </div>
    <br/>
    <br/>
        <div className="product">
          <div className="product-image">
          <img src={"data:image/png;base64,"+productImage} style={{ maxWidth: '100%', verticalAlign:'middle', borderRadius:4 }} />  
          </div>
          <div className="product-details">
            <div style={{fontSize:20, paddingTop:20}} className="product-title"><b>{productName}</b></div>
            {/* <p className="product-description">The best dog bones of all time. Holy crap. Your dog will be begging for these things! I got curious once and ate one myself. I'm a fan.</p> */}
          </div>
          <div style={{paddingTop:20}} className="product-price"><b>$</b> {price}</div>
          <div className="product-quantity">
          <FormControl  sx={{ m: 1, minWidth: 80 }} size="small">
                <InputLabel id="demo-select-small">Qty :</InputLabel>
                <Select
              labelId="demo-select-small"
              id="demo-select-small"
              value={selectQuantity}
              disabled
              label="Category"
              onChange={handleSelectChange}
            >
          <MenuItem value={1}  >{1}</MenuItem>
          <MenuItem value={2}  >{2}</MenuItem>
          <MenuItem value={3}  >{3}</MenuItem>
          <MenuItem value={4}  >{4}</MenuItem>
          <MenuItem value={5}  >{5}</MenuItem>
          <MenuItem value={6}  >{6}</MenuItem>
          <MenuItem value={7}  >{7}</MenuItem>
          <MenuItem value={8}  >{8}</MenuItem>
          <MenuItem value={9}  >{9}</MenuItem>
          <MenuItem value={10}  >{10}</MenuItem>
            </Select>
          </FormControl>
          </div>
      <div style={{paddingTop:20}} className="product-line-price"> <b>$</b>  {totalprice}</div>
          <div className="product-removal" style={{paddingLeft:50, paddingTop:20}}>
          <Button color="secondary" 
               onClick={handleDeleteItem}
            >  <img src={deletecart}style={{height:20, width:20}}/>
            </Button>
          </div>
        </div>
                </div>
        </div>
        {/* <Navbar itemCount2={<CartCount/>}/> */}
    </div>
  )
}
export default GridItem
