// import ReactDOM from "react-dom";
import React, { useState, useEffect, useTransition } from "react";
import axios from "axios";
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import SearchIcon from '@mui/icons-material/Search';
import { Chip, Icon, InputAdornment, Rating } from '@mui/material';
import '../Product/ProductCSS/SearchBox.css'
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import MenuItem from '@mui/material/MenuItem';
import { styled } from '@mui/material/styles';
import { useNavigate } from 'react-router-dom';
import '../Product/ProductCSS/Product.css';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import ProductAdd from "./ProductAdd";
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import ProductAddButton from './ProductAddButton';
import StockCount from "./StockCount";
import Navbar from "../Navbar/Navbar";
import { url } from "../../ServiceApi/ServiceApi";
 
 
const DialogAddSlot = styled(Dialog)(({ theme }) => ({
  '& .MuiDialog-paper': {
    padding: theme.spacing(2),
    minWidth: '900px !important',
  },
  '& .MuiDialogActions-root': {
    padding: theme.spacing(1),
  },
}));

export default function Product() {
  const [cartCount, setCartCount] = React.useState();
console.log(cartCount);
  const isLoggedIn = localStorage.getItem('roles') == 'seller';
  const isadminlogin1 = localStorage.getItem('roles') == 'admin';
  const [currUser, SetCurrUser] = React.useState();
  const [profileEditOpen, setProfileEditOpen] = React.useState(false);
  const [selectedOption, setSelectedOption] = useState("");
  const navigate = useNavigate();
  const [open, setOpen] = React.useState(false);
  const [editopen, seteditOpen] = React.useState(false);
  const [cartOpen, setcartOpen] = React.useState(false);
  const [totalCartOpen, settotalCartOpen] = React.useState(false);
  const handleCartOpen = (id) => {
    setItemproductId(id)
    setcartOpen(true);
  }
  const handleClickOpen = () => {
    setOpen(!open);
    if (open === true) {
      axios.get(api
        //, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
        , {})
        .then(res => {
          setproduct(res.data);
        })
        .catch(e => {
        })
    }
  };
  const handleClose = () => {
    handleClickOpen();
    setproductName('');
    setDescription('');
    setprice('');
    setstock('');
    setunit('');
    setSelectedOption('');
  };
  const handleCartClose = () => {
    setcartOpen(false);
  };
 
  const handleTotalCartClose = () => {
    settotalCartOpen(false);
  };
  const handleClose2 = () => {
    seteditOpen(false);
    setproductName('');
    setDescription('');
    setprice('');
    setstock('');
    setunit('');
    setSelectedOption('');
  };
 
  //  style for edit delte button
  const CustomButton = styled(Button)({
    backgroundColor: '#063970',
    border: 0,
    borderRadius: 40,
    boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
    color: 'white',
    height: 48,
    padding: '0 30px',
    height: 30,
    width: 80,
  });
  const [searchValue, setsearchValue] = useState('');
  const handleSearchValue = (e) => {
    setsearchValue(e.target.value)
  }

  const handleProfileEditClose = () => {
    setProfileEditOpen(false);
  }

  const [products, setproduct] = useState([]);

  function getUserDetails (){
    axios.get(url+'user/'+localStorage.getItem("userId")
    , {})
    //,{ headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
    .then(res => {
      SetCurrUser(res.data);
      if(res.data.role==="buyer" && (res.data.street===null || res.data.city===null
       || res.data.state===null || res.data.country===null)){
          setProfileEditOpen(true);
      }

      if(res.data.role==="seller" && (res.data.bankName===null || res.data.bankAccount===null)){
           setProfileEditOpen(true);
       }
    })
    .catch(e => {

      console.log(e.response.data.message);
    })
  }
  

  

  const api = url+'product/search?productName='+searchValue+'&userId='+localStorage.getItem('userId')
  useEffect(() => {
    getUserDetails ();
    getAllProducts();
    getCartCount();
    if(isLoggedIn){
    const apic = url+'product/category/ordercategory/'+ localStorage.getItem('userId')
    axios.get(apic
      , {})
      //, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      .then(res => {
        setcategory1(res.data);
      })
      .catch(e => {
        console.log(e.message);
      })
    }
  }, [searchValue]);
  const [category1, setcategory1] = useState([]);
  function getAllProducts() {
    axios.get(api
      , {})
      //, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      .then(res => {
        setproduct(res.data);
      })
      .catch(e => {
      })
  }
  function getCartCount(){
    const data = {
      "userId": localStorage.getItem("userId"),
      "orderStatus": "ACTIVE"
    };
    axios.post(url+'order/cart/data', data
    , {})
        //, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
        .then(res => {
          {
            console.log("------", res.data.item.length);
           setCartCount( res.data.item.length);
          }
        })
        .catch(e => {
          console.log(e.message);
        })
  }
  /*  Add New Product      */
  const [snackopen, setsnackOpen] = React.useState(false);
  const [message, setmessage] = useState('');
  const handleClose1 = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setsnackOpen(false);
  };
  const action = (
    <React.Fragment>
      <IconButton
        size="small"
        aria-label="close"
        color="inherit"
        onClick={handleClose1}
      >
        <CloseIcon fontSize="small" />
      </IconButton>
    </React.Fragment>
  );
  const [file, setFile] = useState(null);
  const [productName, setproductName] = useState('');
  const [description, setDescription] = useState('');
  const [price, setprice] = useState('');
  const [stock, setstock] = useState(100);
  const [unit, setunit] = useState('');
 const handlePriceChange = (event) => {
    const inputValue = event.target.value;
    // Allow only numbers and a dot (for decimal places)
    const numericValue = inputValue.replace(/[^0-9.]/g, '');
    // Update the state with the numeric value
    setprice(numericValue);
 }
 const handleStockChange = (event) => {
  const stockValue = event.target.value;
  // Allow only numbers and a dot (for decimal places)
  const numericStockValue = stockValue.replace(/[^0-9.]/g, '');
  // Update the state with the numeric value
  setstock(numericStockValue);
}
  const handleSubmit = () => {
    
    const formData = new FormData();
    if (!file) {
      setmessage('Please upload product image');
      setsnackOpen(true);
      return;
    }
    if (!productName) {
      setmessage('Product is name is required');
      setsnackOpen(true);
      return;
    }
    if (!stock) {
      setmessage('Please Enter the Stock');
      setsnackOpen(true);
      return;
    }
    if (!unit) {
      setmessage('which type of product you add. Please add unit of product');
      setsnackOpen(true);
      return;
    }
    if (!selectedOption) {
      setmessage('Choose category in your product');
      setsnackOpen(true);
      return;
    }
    if (!price) {
      setmessage('Please Enter the procut price');
      setsnackOpen(true);
      return;
    }
    console.log("ADD PRODUCt data ok, needs to send to backend");
    formData.append('imagefile', file);
    formData.append('data', JSON.stringify({
      "productName": productName,
      "description": description,
      "stock": stock,
      "unit": unit,
      "categoryId": selectedOption,
      "price": price,
      "userId": localStorage.getItem('userId')
    }))
    axios.post(url+'product', formData
    , {
      headers: {
        //Authorization: `Bearer ${localStorage.getItem('Token')}`,
        'Content-Type': 'multipart/form-data'
      }
    })
    //, {})
      .then((response) => {
        console.log(response);
        setmessage('Product added and waiting for admin approval');
        setproductName('');
        setDescription('');
        setprice('');
        setstock('');
        setunit('');
        setSelectedOption('');
        handleClickOpen();
      })
      .catch((error) => {
        console.log(error);
        setmessage(error.response.data.message);
        setproductName('');
        setDescription('');
        setprice('');
        setstock('');
        setunit('');
        setSelectedOption('');
      });
  };
  /************  get by id for update in product   **&&&&&&&&&&&********/
  const [updateproductName, setupdateproductName] = useState('');
  const [updateDescription, setupdateDescription] = useState('');
  const [updateprice, setupdatetprice] = useState('');
  const [updatestock, setupdatestock] = useState(100);
  const [updateunit, setupdateunit] = useState('');
  const [updateCategory, setupdateCategory] = useState();
  const [imageId, setimageId] = useState();
  const [updatefile, setupdateFile] = useState(null);
  const [productId, setproductId] = useState('');
  const [updateCategoryId, setupdateCategoryId] = useState();
  const handleDelete = (id) => {
    axios.delete(url+'product/' + id +'/'+localStorage.getItem('userId')
    // , {
    //   headers: {
    //     Authorization: `Bearer ${localStorage.getItem('Token')}`
    //   }
    // })
    , {})
      .then((res, status) => {
        console.log(res.data, status);
        getAllProducts();
      })
      .catch((error) => {
        setsnackOpen(true);
        setmessage(error.response.data.message);
        setOpen(false);
      });
  }
  const handleEdit = (id) => {
    seteditOpen(true);
    axios.get(url+'product/' + id
    // , {
    //   headers: {
    //     Authorization: `Bearer ${localStorage.getItem('Token')}`
    //   }
    // })
    , {})
      .then((res) => {
        setproductId(res.data.productId);
        setupdateproductName(res.data.productName);
        setupdateDescription(res.data.description);
        setupdatetprice(res.data.price);
        setupdatestock(res.data.stocks);
        setupdateunit(res.data.unit);
        setupdateCategoryId(res.data.category.categoryId);
        setupdateCategory(res.data.category.categoryName)
        setimageId(res.data.productImage.imageId);
      })
      .catch((error) => {
        setmessage('Cant empty field')
        setOpen(false);
      });
  }
  /********   get Id end    ****************/
  /*  For edit Api
    */
  const handleEdit2 = (id) => {
    setsnackOpen(true);
    if (updateproductName == '') {
      setmessage('Product name cannot be empty')
      return;
    }
    if (updateDescription== '') {
      setmessage('Description cant be empty')
      return;
    }
    if (updatestock == '') {
      setmessage('Stock cant empty')
      return;
    }
    if (updateunit == '') {
      setmessage('Please Give unit value')
      return;
    }
    if (updateprice == '') {
      setmessage('Price cannot be empty')
      return;
    }
    if (imageId == '') {
      setmessage('Please upload product image')
      return;
    }
    const formData = new FormData();
    formData.append('imagefile', updatefile);
    formData.append('data', JSON.stringify({
      "productName": updateproductName,
      "description": updateDescription,
      "stock": updatestock,
      "unit": updateunit,
      "categoryId": updateCategoryId,
      "price": updateprice,
      "imageId": imageId,
      "userId": localStorage.getItem('userId')
    }))
    axios.put(url+'product/' + id, formData
    // , {
    //   headers: {
    //     Authorization: `Bearer ${localStorage.getItem('Token')}`,
    //     'Content-Type': 'multipart/form-data'
    //   }
    // })
    , {})
      .then((response) => {
        setmessage('product updated')
        console.log(response)
        seteditOpen(false);
        setOpen(false);
        getAllProducts();
      })
      .catch((error) => {
        console.log(error);
        setOpen(false);
      });
  }
  const ResponsiveImage = ({ src, alt }) => {
    return (
      <div className="image-wrapper">
        <img className="responsive-image" src={src} alt={alt} height='170' />
      </div>
    );
  };
  const [itemproductId, setItemproductId] = useState();
 
  const AddtocartButton = ({ children, onClick }) => {
    return (
      <button className="add-to-cart-button" onClick={onClick}>
        {children}
      </button>
    );
  };
  const EditButton = ({ children, onClick }) => {
    return (
      <button className="edit-button" onClick={onClick}>
        {children}
      </button>
    );
  };
  const handleUpdatecategory = (event) => {
    setupdateCategoryId(event.target.value)
  }

  const[isapprove ,setIsApprove] = useState("");
  function checkproductStatus (id, status) {
    
    const checkedData ={"productId":id,
                        "userId": localStorage.getItem('userId'),
                        "status":status                    
  }
    
    console.log(id);
    axios.put(url+'product' ,checkedData 
   // , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
   , {})
    .then(res => {
      
      setsnackOpen(true);
      setIsApprove(res.data.status)

      if(res.data.status=="APPROVED"){
        setmessage("product approved");
        
      }
      else if(res.data.status=="PENDING"){
        setmessage("Product Pending");
         
      }
      getAllProducts();
      
       
    })
    .catch(e => {

      console.log(e.message);
      getAllProducts();
    })
 
  }




  return (
    <div>
      <>
        <Navbar itemCount2 = {cartCount}/>
      </>
      <br />
      <br />
      <br /><br />
      {isLoggedIn ? (
        <Box    className="addBtn" sx={{ "& > :not(style)": { m: 1 }}}>
          <button
            onClick={handleClickOpen}
            style={{
              float: "right",
              marginLeft: "20px",
              // marginTop: "40.5px",
              border: "2px solid #7bb8b1",
              color: "white",
              padding: "5px 5px",
              backgroundColor: "#7bb8b1",
              cursor: "pointer",
              fontSize: "16px",
              borderRadius: "25px",
            }}
          >
            Add Product
          </button>
          <TextField
            
            size="small"
            InputProps={{
              endAdornment: (
                <InputAdornment>
                  <IconButton>
                    <SearchIcon />
                  </IconButton>
                </InputAdornment>
              ),
            }}
            style={{width: '85%', borderRadius:'50px'}}
            placeholder="Search Product or category..."
            className="searchBar"
            type="text"
            // value={searchText}
            onChange={handleSearchValue}
          />
        </Box>
      ) : (
        <Box  className="addBtn" sx={{ "& > :not(style)": { m: 1 } }}>
          <br />
          <TextField
           size="small"
            InputProps={{
              endAdornment: (
                <InputAdornment>
                  <IconButton>
                    <SearchIcon />
                  </IconButton>
                </InputAdornment>
              ),
            }}
            style={{width: '95%', borderRadius:'50px'}}
            placeholder="Search Product or category..."
            className="userSearchBar"
            type="text"
            // value={searchText}
            onChange={handleSearchValue}
          />
        </Box>
      )}
      
      <Button sx={{}}></Button>
       
      <Box sx={{ flexGrow: 1, display: 'flex', justifyContent: 'center' }} >
        <Grid container spacing={1} style={{ direction: 'row', padding: '15px' }}>
          {products.map((items) => (
            <Grid disabled={items.stock === 0} item xs={6} md={6} lg={3} style={{ marginBottom: 15 }}>
              <Card sx={{ maxWidth: 250, maxHeight: 350, minHeight:'350px' }}>
                <CardMedia>
                  <ResponsiveImage src={'data:image/png;base64,' + items.data} alt="Alt text for image" />
                </CardMedia>
                <CardContent>
                <Typography fontWeight={700} style={{ fontStyle: 'bold', color: "#000000", }} component="div">
                    {items.productName}&nbsp; 
                  </Typography>
                {/* <Rating color="warning" name="half-rating" style={{color:'orange !important'}} defaultValue={items.ratings} precision={0.5} /> */}
              <div style={{display:'flex'}}><Typography style={{ top: '-0.75em', fontSize:'10px' }}>$ </Typography>&nbsp;<Typography fontWeight={700}>{items.price}</Typography></div>
                <div className="d-flex justify-content-between">
                
                  <StockCount value={items.stock} />
                  {isadminlogin1 || isLoggedIn ? (
                    <>
                     <Chip label={items.status} size='small' color={items.status==="PENDING"? 'error': 'success'} style={{fontSize: '0.7rem', fontFamily:'saira'}}></Chip>
                    </>
                  ) : (
                    ""
                  )}

                    </div>
                  
                  {isLoggedIn ? (
                    <>
                     
                    </>
                  ) : (
                    ""
                  )}
                
                <br></br>
                {(() => {
      if (isadminlogin1) {
        return  <div>
         
        <Button onClick={() => checkproductStatus(items.productId, "approved")} >
        Approve </Button>
        <Button onClick={() => checkproductStatus(items.productId, "pending")} >
        Decline </Button>
      </div>;
      } else if (isLoggedIn) {
        return  <>
                     
         <Button onClick={() => handleEdit(items.productId)} variant="outlined" size="small"><EditIcon size="large"  />EDIT</Button>
         &nbsp;&nbsp;&nbsp;&nbsp;
         <Button onClick={() => handleDelete(items.productId)} variant="outlined"size="small"><DeleteIcon size="large"  />DELETE</Button>
      </>;
      } else {
        return <>
        <button className="add-to-cart-button" 
        disabled={items.stock===0 ? true : false}
        onClick={() => handleCartOpen(items.productId)}>
        Add To cart
      </button>
      </>
      }
    })()}
                
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Box>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle><Typography fontSize={"1.7rem"}>Adding New Product</Typography></DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Product Name"
            type="email"
            fullWidth
            variant="standard"
            value={productName}
            onChange={(e) => setproductName(e.target.value)}
          />

          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Description"
            type="email"
            fullWidth
            rows={"4"}
            multiline
            variant="standard"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />

          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Price "
            type="text"
            fullWidth
            variant="standard"
            value={price}
            onChange={handlePriceChange}
          />
          {/* <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Stock"
            type="text"
            fullWidth
            variant="standard"
            value={stock}
            onChange={handleStockChange}
          /> */}
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Unit"
            type="text"
            fullWidth
            variant="standard"
            value={unit}
            onChange={(e) => setunit(e.target.value)}
          />
          <FormControl variant="standard" sx={{ m: 1, minWidth: 150 }}>
            <InputLabel id="demo-simple-select-standard-label">Category</InputLabel>
            {/* <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Product Name"
            type="email"
            fullWidth
            variant="standard"
            value={getCategoryId}
            onChange={(e) => setCategory(e.target.value)}
          /> */}
            <Select
              labelId="demo-select-small"
              id="demo-select-small"
              value={selectedOption}
              label="Category"
              onChange={(event) => setSelectedOption(event.target.value)}
            >
              {category1.map((item) => (
                <MenuItem value={item.categoryId}   >
                  {item.categoryName}</MenuItem>
              ))}
            </Select>
          </FormControl>
          <Stack sx={{ marginTop: 2 }} direction="row" alignItems="center" spacing={2}>
            <Button variant="contained" component="label">
              Uploading Product image
              <input hidden value={''} onChange={(e) => setFile(e.target.files[0])} type="file" />
            </Button>
          </Stack>
        </DialogContent>
        <DialogActions>
          <ProductAddButton label="Add" onClick={handleSubmit} />
          <ProductAddButton label="Cancel" onClick={handleClose} />
          {/* <button  onClick={handleSubmit}>Add</button>
          <button onClick={handleClose}>Cancel</button> */}
        </DialogActions>
      </Dialog>
      {/* for uploading a product */}
      <Dialog open={editopen} onClose={handleClose2}>
        <DialogTitle>Updating a Product</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Product Name"
            type="text"
            fullWidth
            variant="standard"
            value={updateproductName}
            onChange={(e) => setupdateproductName(e.target.value)}
          />
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Description"
            type="text"
            fullWidth
            rows={"4"}
            multiline
            variant="standard"
            value={updateDescription}
            onChange={(e) => setupdateDescription(e.target.value)}
          />
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Price "
            type="text"
            fullWidth
            variant="standard"
            value={updateprice}
            onChange={(e) => setupdatetprice(e.target.value)}
          />
          {/* <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Stock"
            type="text"
            fullWidth
            variant="standard"
            value={updatestock}
            onChange={(e) => setupdatestock(e.target.value)}
          /> */}
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="units"
            type="text"
            fullWidth
            variant="standard"
            value={updateunit}
            onChange={(e) => setupdateunit(e.target.value)}
          />
          <FormControl variant="standard" sx={{ m: 1, minWidth: 150 }} size="small">
            <InputLabel id="demo-simple-select-standard-label">Category</InputLabel>
            <Select
              labelId="demo-select-small"
              id="demo-select-small"
              value={updateCategoryId}
              label="Category"
              onChange={handleUpdatecategory}
            >
              {/* <MenuItem value={updateCategoryId} selectedOption={updateCategory} >{updateCategory}</MenuItem> */}
              {category1.map((item) => (
                <MenuItem value={item.categoryId} >
                  {item.categoryName}
                </MenuItem>
              ))}
              {/* **************************   */}
            </Select>
          </FormControl>
          <Stack sx={{ marginTop: 2 }} direction="row" alignItems="center" spacing={2}>
            <Button variant="contained" component="label">
              Uploading Product image
              <input hidden value={''}
                onChange={(e) => setupdateFile(e.target.files[0])}
                type="file" />
            </Button>
          </Stack>
        </DialogContent>
        <DialogActions>
          {/* <Button onClick={handleClose2}>Cancel</Button>
          <Button onClick={() => handleEdit2(productId)}>Update</Button> */}
          <ProductAddButton label="Update" onClick={() => handleEdit2(productId)} />
          <ProductAddButton label="Cancel" onClick={handleClose2} />
        </DialogActions>
      </Dialog>
      {/* end update dialog box */}
      {/* add to cart dialog box */}
      {/* Total cart dialog box */}
      <Dialog open={totalCartOpen} onClose={handleCartClose}>
        <DialogTitle>Total Item in your cart</DialogTitle>
        <DialogContent>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleTotalCartClose}>Cancel</Button>
          <Button onClick={handleSubmit}>Add</Button>
        </DialogActions>
      </Dialog>
      {/* total cart dialog box end */}
      <DialogAddSlot
        fullWidth={'md'}
        open={cartOpen}
        onClose={handleCartClose}
      >
        <DialogTitle><Typography fontSize={'1.2rem'}>Adding Product in your cart</Typography></DialogTitle>
        {<ProductAdd handleCartClose={handleCartClose} productId={itemproductId} setCartCount={setCartCount}
        //  handleClose={}
        />}
      </DialogAddSlot>

      


      <Snackbar
        open={snackopen}
        autoHideDuration={6000}
        onClose={handleClose1}
        message={message}
        anchorOrigin={{
          vertical: "top",
          horizontal: "center"
        }}
        action={action}
      />
     {/* <Navbar itemCount2={<CartCount/>}/> */}
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      {/* <Divider>CENTER</Divider> */}
      {
         
      }
    </div>
  )
}
