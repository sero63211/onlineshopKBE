import React, { useState, useEffect } from "react";
import axios from "axios";
import { styled } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
import MyOrderCard from "./MyOrderCard";
import orderEmpty from '../../images/order_empty.jpg';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton'; 
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Navbar from "../Navbar/Navbar";
import { url } from "../../ServiceApi/ServiceApi";
const Img = styled('img')({
  margin: 'auto',
  display: 'block',
  maxWidth: '100%',
  maxHeight: '100%',
});
const ExpandMore = styled((props) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
  marginLeft: 'auto',
  transition: theme.transitions.create('transform', {
    duration: theme.transitions.duration.shortest,
  }),
}));
function MyOrders(cartCount1) {
  const [expanded, setExpanded] = React.useState(true);
  const handleExpandClick = () => {
    setExpanded(!expanded);
  };
  const [isTrue, setIsTrue] = useState();
  const [isTrue1, setIsTrue1] = useState("");
  const [order, setorder] = useState([]);
  const [totalAmount, setTotalAmount] = useState();
  const data = {
    "userId": localStorage.getItem("userId"),
    "orderStatus": "ACTIVE"
  };
  const [cartCount, setCartCount] = React.useState();
 const [item, setitem] = useState([]);
 const[dateTime, setDateTime] = useState();
 const[totalAmount1, setTotalAmount1] = useState(0);
  function orderlist() {
    axios.get(url+'order/'+ localStorage.getItem('userId')
    //, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
    , {})
    .then(res => {
      if(res.data==0){
        setIsTrue(orderEmpty);
          setIsTrue1("Your order list is currently empty.");
      }
      setorder(res.data);
      setTotalAmount(res.data.totalAmount);
      for(var i = 0; i<order.length; i++){
          setitem(order[0].cart.item)
           setDateTime(order[0].dateTime);
          setTotalAmount1(order[0].totalAmount);
      }
    })
    .catch(e => {
      console.log(e);
    })
  }
  useEffect(() => {
    orderlist();
  }, []);
  axios.post(url+'order/cart/data', data
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
  const handleVariableChange = (receivedVariable) => {
    setDateTime(receivedVariable); 
    console.log(receivedVariable);
    // Do something with the receivedVariable in the parent component
  };
  return (
    <div>
      <Navbar 
      itemCount2 = {cartCount}
      />
      <br />
      <br />
      <br />
      <h2 className="text-charcoal d-none d-sm-block">Your Orders</h2>
      <Card   style={{ marginBottom: '20px', maxWidth: '80%', marginLeft: '8%', backgroundColor:'#f1f2e1' }}>
      <CardHeader
         title="Your recent orders" >
      </CardHeader>
      <CardContent>
          <Typography>
            Thank you for placing an order with <b>Online shop</b>. We appreciate your business! Below, you will find the order details for your reference:

          </Typography>
      </CardContent>
      <CardActions disableSpacing>
        {/* <IconButton aria-label="add to favorites">
          <FavoriteIcon />
        </IconButton>
        <IconButton aria-label="share">
          <ShareIcon />
        </IconButton> */}
        <ExpandMore
          expand={expanded}
          onClick={handleExpandClick} 
          aria-expanded={expanded}
          aria-label="show more"
        >
          <ExpandMoreIcon />
        </ExpandMore>
      </CardActions>
      <Collapse in={expanded} timeout="auto" unmountOnExit>
        <CardContent>
        { order.map((e, index) => ( 
<>
       <MyOrderCard onVariableChange={handleVariableChange} item={e.cart.item}  dateTime={e.dateTime} totalAmount={e.totalAmount}/>
        </>
))} 
        </CardContent>
      </Collapse>
    </Card>
      {/* <MyOrderCard item={item}  dateTime={dateTime} totalAmount={totalAmount1}/> */}
      <div style={{display: "flex", alignItems:"center", justifyContent:'center', height:100, marginTop:70 }}>
           <img height={200}     src={isTrue}/>
           </div>
             <p style={{display: "flex", alignItems:"center", justifyContent:'center', paddingTop:59, fontSize:20}}><b>{isTrue1}</b></p>
    {/* {orderInfo ?  <NotFound/> : null} */}
    {/* 
    <Paper
          sx={{
            p: 2,
            margin: 'auto',
            maxWidth: 500,
            flexGrow: 1,
            backgroundColor: (theme) =>
              theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
          }}
        >
          <Grid container spacing={2}> 
          {/* productImage, productName, quantity, price, totalprice, dateTime, totalAmount */}
            {/* <p>{e.dateTime}</p>
            <p>{e.totalAmount}</p>
              {e.cart.item.map((a, index) => (   
                <>
          <Grid item xs={6}> 
          <ButtonBase sx={{ width: 128, height: 128 }}>
          <Img alt="complex" src={'data:image/png;base64,' + a.product.productImage.imageData} />
        </ButtonBase> 
        </Grid>
        <Grid item xs={6}> 
                  <Typography variant="body2" color="text.secondary">
                  {a.product.productName}  <br />   {a.product.price}X{a.quantity}
                  </Typography>
                  <Typography variant="subtitle1" component="div">
                <span>&#x20B9;</span>&nbsp;  {totalAmount}
                </Typography>
                </Grid>
                  </>
              ))}        
            </Grid>
        </Paper>
        <br></br></>
    ))} 
       */}
  {/* <Navbar itemCount2={<CartCount/>}/> */}
    </div>
  )
}
export default MyOrders
