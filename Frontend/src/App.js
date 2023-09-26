import React, {    } from "react";
 
import Login from "./component/login/Login";
import RegistrationForm from "./component/RegisterForm/RegistrationForm";
import Product from "./component/Product/Product";
import { createTheme } from "@mui/material/styles";
import { ThemeProvider } from "@mui/material/styles";
import { Route, Routes } from 'react-router-dom';
import MyOrders from "./component/Order/MyOrders"; 
  
import AddToCartIcon from "./component/Cart/AddToCartIcon";
 
 
 
 
 import axios from "axios";
 
 
import InActive from "./component/login/InActive";
import { url } from "./ServiceApi/ServiceApi";
import Category from "./component/Category/Category";
import AdminOrders from "./component/Order/AdminOrders";
// import Category from "./component/Category/Category";
 




export default function App() {

  const [cartCount, setCartCount] = React.useState();

  function getCartCount(){
    const data = {
      "userId": localStorage.getItem("userId"),
      "orderStatus": "ACTIVE"
    };
    axios.post(url+'order/cart/data', data
       // , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
       , {})
        .then(res => {
   
          {
            
           setCartCount( res.data.item.length);
          }
  
  
        })
        .catch(e => {
  
          console.log(e.message);
  
        })
  }


  getCartCount();




//   const ProtectedRoutes = ({auth}) => {
    
//     return (auth === true ? <Outlet /> : <Navigate to="/" replace/>)
// }
const theme = createTheme({
  typography: {
   "fontFamily": `Saira`,
   "fontSize": '1.5 rem',
   "fontWeightLight": 300,
   "fontWeightRegular": 400,
   "fontWeight": 700
  }
});

  return (
    <ThemeProvider theme={theme}>
    <div className="App">

    
    <Routes>
    {/* <Route path='/' element={isAuth ? <Navigate to="/" /> : <Login />} /> */}
      <Route  path="/" element={<Login/>} />
      <Route path="/register" element={<RegistrationForm/>} /> 
      <Route path="/home" element={<Product setCartCount={setCartCount} />} />
      <Route path="/category" element={<Category/>} />
      <Route path="/myorders" element={<MyOrders setCartCount={setCartCount}/>}/>
      <Route path="/mycart" element={<AddToCartIcon setCartCount={setCartCount}/>}></Route>
      <Route path="/noaccess" element={<InActive/>}></Route>
      <Route path="/orders" element={<AdminOrders/>}></Route>
     
      
     
   
      
     
    
   
        
    </Routes>



      
       

 

    </div>
    </ThemeProvider>
  );
}

