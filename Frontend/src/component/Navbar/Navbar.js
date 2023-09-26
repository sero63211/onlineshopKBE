import React, { useState, useEffect } from "react";
import axios from "axios";
import Avatar from '@mui/material/Avatar';
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Stack from '@mui/material/Stack';
import { styled } from '@mui/material/styles';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Badge from '@mui/material/Badge';
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';
  
import MoreIcon from '@mui/icons-material/MoreVert';
import AddShoppingCartIcon from '@mui/icons-material/AddShoppingCart';
import { useNavigate } from 'react-router-dom';
import '../Product/ProductCSS/Product.css';
  
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import Shopping from '../../images/logo_secure_cart.png';
import DialogTitle from '@mui/material/DialogTitle';
import Button from '@mui/material/Button';
import AppBar from '@mui/material/AppBar';
import Slide from '@mui/material/Slide';
import { url } from "../../ServiceApi/ServiceApi";
import { Divider } from "@mui/material";
const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});
const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : 'white',
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));
const useStyles = styled({
  root: {
    '&:hover': {
      textDecoration: 'underline', // add any other styles you want here
    },
  },
});
function Navbar({ itemCount2 }) {
  const [itemCount, setitemCount] = useState(itemCount2);
  console.log("---navbar cat number--", itemCount2);
  // setitemCount(itemCount2);
  const classes = useStyles();
  const data = {
    "userId": localStorage.getItem("userId"),
    "orderStatus": "ACTIVE"
  };
  const [cartInfo, setCartInfo] = useState([]);
  const [cartid, setCartId] = useState(0);
  async function handleClickOpenCart() {
    navigate('/mycart');
    try {
      await axios.post(url+'order/cart/data', data 
        //, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
        , {})
        .then(res => {
          console.log(res.data);
          setCartId(res.data.cartId)
          setCartInfo(res.data.item);
          console.log(cartInfo);
        })
    } catch (error) {
      console.error(error);
      if (!cartInfo) {
        return navigate('/notfound');
      }
    }
  };
  const navigate = useNavigate();
  const handleCategory = () => {
    navigate('/category');
  };
  // const handlePayments = () => {
  //   navigate('/payments');
  // };
  const handleUser = () => {
    navigate('/user');
  };
  const handleAdminPayments = () => {
    navigate('/admin-payments');
  };
  const handleAdminOrders = () => {
    navigate('/orders');
  };
  const handleMyOrders = () => {
    navigate('/myorders');
  };
  const handleQuestions = () => {
    navigate('/questions');
  };
  const handleProduct = () => {
    navigate('/home');
  };
  const handleLogout = () => {
    setAnchorEl(null);
    handleMobileMenuClose();
    navigate('/');
    localStorage.removeItem('Token')
    localStorage.removeItem('roles')
    localStorage.removeItem('username')
    localStorage.removeItem('email')
    localStorage.removeItem('profile')
    localStorage.removeItem('lastname');
    localStorage.removeItem('userId');
  };

  const [editOpen, setEditOpen] = React.useState(false);
  const handleUserClose = () => {
    setEditOpen(false);
  };

  const[userId, setuserId] = useState();
  const[firstName, setFirstName] = useState();
  const[lastname, setLastname] = useState();
  const[emailId, setEmailId] = useState();
  const[street, setStreet] = useState();
  const[city, setCity] = useState();
  const[state, setState] = useState();
 const[country, setCountry] = useState();
  const[profilePicture, setProfile] = useState(null);
  
  const handleEditProfile = () => {
     
    setEditOpen(true);
     
     
     
  };



  const [anchorEl, setAnchorEl] = React.useState(null);
  const [mobileMoreAnchorEl, setMobileMoreAnchorEl] = React.useState(null);
  const isMenuOpen = Boolean(anchorEl);
  const isMobileMenuOpen = Boolean(mobileMoreAnchorEl);
  const handleProfileMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleMobileMenuClose = () => {
    setMobileMoreAnchorEl(null);
  };
  const handleMenuClose = () => {
    setAnchorEl(null);
    handleMobileMenuClose();
  };
  const handleMobileMenuOpen = (event) => {
    setMobileMoreAnchorEl(event.currentTarget);
  };
  const menuId = 'primary-search-account-menu';
  const renderMenu = (
    <Menu
      anchorEl={anchorEl}
      // anchorOrigin={{
      //   vertical: 'top',
      //   horizontal: 'right',
      // }}
      id={menuId}
      keepMounted
      // transformOrigin={{
      //   vertical: 'top',
      //   horizontal: 'right',
      // }}
      open={isMenuOpen}
      onClose={handleMenuClose}

      anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
    transformOrigin={{ vertical: 'top', horizontal: 'center' }}
    >
      
      {/* <MenuItem onClick={handleEditProfile}>Profile</MenuItem>
      <Divider/> */}
      <MenuItem onClick={handleLogout}>Log out</MenuItem>
    </Menu>
  );
  const [color, setColor] = useState('default')
  const handleClickChild = () => {
    // if(!isLoggedIn){
    setColor('error');
   
  }
  useEffect(() => {

 



    handleClickChild();
  }, []);
  const mobileMenuId = 'primary-search-account-menu-mobile';
  const renderMobileMenu = (
    <Menu
      anchorEl={mobileMoreAnchorEl}
      anchorOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      id={mobileMenuId}
      keepMounted
      transformOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      open={isMobileMenuOpen}
      onClose={handleMobileMenuClose}
    >
      <MenuItem>
        <Button onClick={handleProduct} sx={{ color: '#000000' }}>
          Product
        </Button>
      {localStorage.getItem('roles') === 'admin' ? (
        <>
          <Button className={classes.root} onClick={handleUser} sx={{ color: 'white' }}>
              Users
            </Button>
        </>
      ) : (
        ''
      )}
      {localStorage.getItem('roles') === 'seller' ? (
        <>
          <MenuItem>
            <Button onClick={handleCategory} sx={{ color: '#000000' }}>
              Category
            </Button>
          </MenuItem>
        </>
      ) : (
        ''
      )}

      {/* {localStorage.getItem('roles') === 'seller' ? (
        <>
          <MenuItem>
            <Button onClick={handlePayments} sx={{ color: '#000000' }}>
              Shipping
            </Button>
          </MenuItem>
        </>
      ) : (
        ''
      )} */}
</MenuItem>
      {localStorage.getItem('roles') !== 'user' ? (
        <>
        </>
      ) : (
        <MenuItem>
          <IconButton
            size="large"
            aria-label="show 1 new notifications"
            color="inherit"
            onClick={handleClickOpenCart}
          >
            <Badge badgeContent={
              itemCount2
            } color={color}>
              <AddShoppingCartIcon />
            </Badge>
          </IconButton>
          <p>Cart</p>
        </MenuItem>
      )}
      <MenuItem>
        {localStorage.getItem('roles') !== 'user' ? (
          <>
          </>
        ) : (
          <Button onClick={handleMyOrders} sx={{ fontFamily: "revert-layer", color: '#000000' }}>
            My Orders
          </Button>
        )}
      </MenuItem>
      <MenuItem onClick={handleProfileMenuOpen}>
        <IconButton
          size="large"
          aria-label="account of current user"
          aria-controls="primary-search-account-menu"
          aria-haspopup="true"
          color="inherit"
        >
          <Avatar src={localStorage.getItem('profile')}>
          <img height={40} width={40} src={localStorage.getItem('profilePicture')}/>
          </Avatar>
        </IconButton>
      </MenuItem>
    </Menu>
  );
   
  const handleOpenHome = () => {
    navigate('/home');
  }
  return (
    <div>
      <Box >
        <AppBar elevation={0} sx={{ background: '#262673', color: 'white', borderBottom:'1px solid antiquewhite'}} position="fixed">
          <Toolbar>
            <img onClick={handleOpenHome} style={{ height: 60, width: 60, backgroundColor: 'transparent', cursor: 'pointer', display: { xs: 'none', md: 'flex' }, mr: 1 }} src={Shopping} />
            <Typography
              variant="h6"
              noWrap
              fontFamily="cursive"
              fontSize={"22px"}
              fontWeight={700}
              color={"orange"}
            > Online shop
            </Typography>
            <Typography
              variant=""
              noWrap
              component="div"
              sx={{ paddingLeft: 5, display: { xs: 'none', sm: 'block' } }}
            >
            </Typography>
            <Box
              component="form"
              sx={{
                paddingLeft: 5,
                color: 'white'
              }}
              noValidate
              autoComplete="off"
            >
            </Box>
            <Box sx={{ display: { xs: 'none', sm: 'block' } }}>
            </Box>
            <Box sx={{ flexGrow: 1 }} />
            <Box sx={{ display: { xs: 'none', md: 'flex' }, color: 'white' }}>
              <Button
                className={classes.root}
                onClick={handleProduct} sx={{ color: 'white' }}>
                <Typography fontWeight={700}>Product</Typography>
              </Button>
              {localStorage.getItem('roles') === 'seller' ? (
                <>
                  <Button
                    className={classes.root}
                    onClick={handleCategory} sx={{ color: 'white' }}>
                    <Typography fontWeight={700} >Category</Typography>
                  </Button>
                </>
              ) : (
                ''
              )}
              {/* {localStorage.getItem('roles') === 'seller' ? (
        <>
          <MenuItem>
            <Button onClick={handlePayments} sx={{ color: '#000000' }}>
              
              <Typography fontWeight={700} >Shipping</Typography>
            </Button>
          </MenuItem>
        </>
      ) : (
        ''
      )} */}
{localStorage.getItem('roles') === 'admin' ? (
        <>
            <Button className={classes.root} onClick={handleUser} sx={{ color: 'white' }}>
              <Typography fontWeight={700}>Users</Typography>
            </Button>
        </>
      ) : (
        ''
      )}

{localStorage.getItem('roles') === 'admin' ? (
        <>
            <Button className={classes.root} onClick={handleAdminPayments} sx={{ color: 'white' }}>
              <Typography fontWeight={700}>Payments</Typography>
            </Button>
        </>
      ) : (
        ''
      )}

{localStorage.getItem('roles') === 'admin' ? (
        <>
            <Button className={classes.root} onClick={handleAdminOrders} sx={{ color: 'white' }}>
              <Typography fontWeight={700}>Orders</Typography>
            </Button>
        </>
      ) : (
        ''
      )}
              
              {localStorage.getItem('roles') !== 'user' ? (
                <>
                </>
              ) : (
                <Button onClick={handleMyOrders} sx={{ fontFamily: "revert-layer", color: 'white' }}>
                  <Typography fontWeight={700}>My Orders</Typography>
                </Button>
              )}
              
                {/* <Button onClick={handleQuestions} sx={{ fontFamily: "revert-layer", color: 'white' }}>
                  <Typography fontWeight={700}>QUESTIONS</Typography>
                </Button> */}

                {localStorage.getItem('roles') !== 'user' ? (
                <>
                </>
              ) : (
                <IconButton
                  size="large"
                  aria-label="show 17 new notifications"
                  color="inherit"
                  onClick={handleClickOpenCart}
                >
                  <Badge
                    badgeContent={
                      itemCount2
                    }
                    color={color}>
                    <AddShoppingCartIcon />
                  </Badge>
                </IconButton>
              )}
              <Toolbar>
                <Typography fontWeight={700} style={{ cursor: 'pointer' }}>
                  {localStorage.getItem('username') + ' '}{localStorage.getItem('lastname')}
                </Typography>
              </Toolbar>
              <Tooltip title={localStorage.getItem('username')}>
                <IconButton onClick={handleProfileMenuOpen} aria-controls={menuId} sx={{ p: 0 }}>
                  {
                    localStorage.getItem('profilePicture') ? (
<Avatar src={localStorage.getItem('profile')}>
                  <img height={40} width={40} src={'data:image/png;base64,' +localStorage.getItem('profilePicture')}/>
                  </Avatar>
                    ) : (
                      <Avatar src={localStorage.getItem('profile')}>
                  </Avatar>
                    )
                  }
                  
                </IconButton>
              </Tooltip>
              {/* <IconButton
              size="large"
              edge="end"
              aria-label="account of current user"
              aria-controls={menuId}
              aria-haspopup="true"
              onClick={handleProfileMenuOpen}
              color="inherit"
            >
              <AccountCircle />
            </IconButton> */}
            </Box>
            <Box sx={{ display: { xs: 'flex', md: 'none' } }}>
              <IconButton
                size="large"
                aria-label="show more"
                aria-controls={mobileMenuId}
                aria-haspopup="true"
                onClick={handleMobileMenuOpen}
                color="inherit"
              >
                <MoreIcon />
              </IconButton>
            </Box>
          </Toolbar>
        </AppBar>
        {renderMobileMenu}
        {renderMenu}
      </Box>

      
          



    </div>
  )
}
export default Navbar
