import React, { useState, useEffect } from "react";
import axios from "axios";
import { styled } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
// import MyOrderCard from "./MyOrderCard";
import orderEmpty from '../../images/order_empty.jpg';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import CircularProgress from '@mui/material/CircularProgress';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Navbar from "../Navbar/Navbar";
import { url } from "../../ServiceApi/ServiceApi";
import { Avatar, Box } from "@mui/material";
import moment from "moment/moment";
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
function AdminOrders() {
  const [order, setorder] = useState();
  function orderlist() {
    axios.get(url + 'order'
      //, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
      , {})
      .then(res => {
        console.log(res.data);
        setorder(res.data);
      })
      .catch(e => {
        console.log(e);
      })
  }

  useEffect(() => {
    orderlist();
  }, []);

  return (
    <div>
      <Navbar
      />
      <br />
      <br />
      <br />
      <br />
      
     
      
      {order ? (
        order.map(each => (
          <>
          <Card style={{ padding: '10px', backgroundColor: 'aliceblue', width: '90%' }}>
            {
              each ? (
                <div>
                  <Typography style={{ flexDirection: 'row', display: 'flex', alignItems: "center" }}>
                    {
                      each.user.profilePicture ? (
                        <>
                          <Avatar src={each.user.profilePicture} style={{ width: "60px", height: '60px' }}>
                            <img height={60} width={60} src={'data:image/png;base64,' + each.user.profilePicture} />
                          </Avatar>
                          &nbsp;<Typography style={{ float: 'left' }}>{each.user.firstName} {each.user.lastName}</Typography>

                        </>
                      ) : (
                        <>
                          <Avatar src={each.user.profilePicture} style={{ width: "60px", height: '60px' }}>
                          </Avatar>
                          &nbsp;<Typography style={{ float: 'left' }}>{each.user.firstName} {each.user.lastName}</Typography>
                        </>
                      )


                    }

                    <Typography variant="subtitle1" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>at&nbsp;{moment(each.dateTime).format("DD-MM-YYYY HH:mm:ss")}</i> </Typography>

                  </Typography>

                  <Grid container>
                    <Grid md={6}>
                      <Card style={{padding:'20px'}}>
                      <Box style={{padding:'20px'}}>
                    <Typography fontFamily={"Saira"} fontWeight={700}>ADDRESS:</Typography> 
                    <Typography fontFamily={"Saira"} fontWeight={500}>{each.user.street}</Typography> 
                    <Typography fontFamily={"Saira"} fontWeight={500}>{each.user.city}</Typography> 
                    <Typography fontFamily={"Saira"} fontWeight={500}>{each.user.state}</Typography> 
                    <Typography fontFamily={"Saira"} fontWeight={500}>{each.user.country}</Typography>
                    </Box>
                    <Box style={{padding:'20px'}}>
                    <Typography fontFamily={"Saira"} fontWeight={700}>PAYMENTS:</Typography> 
                    <Typography fontFamily={"Saira"} fontWeight={500}>{each.orderPayment.referenceNumber}</Typography> 
                    <Typography fontFamily={"Saira"} fontWeight={500}>{each.orderPayment.paymentOption}</Typography> 
                    </Box>
                    </Card>
                    </Grid>

                    <Grid md={6}>
                      {
                        each.cart.item.map((eitem, ind) => (
                          <>

                            <Card key={ind} style={{ padding: '20px', margin: '10px' }}>
                              <Grid container>
                                <Grid md={4}>
                                  <div className="product-image">
                                    <img src={"data:image/png;base64," + eitem.product.imageData} style={{ width: '120px', height: '120px', verticalAlign: 'middle', borderRadius: 4 }} />
                                    <Typography fontFamily={"Saira"} fontWeight={700}>{eitem.product.productName}</Typography>
                                  </div>
                                </Grid>

                                <Grid md={8}>
                                  <table style={{ borderStyle: 'groove', marginTop: '0px', width: '100%' }}>
                                    <tbody>
                                      <tr>
                                        <td>
                                          <Typography>QUANTITY</Typography>
                                        </td>
                                        <td>
                                          <Typography>{eitem.quantity}</Typography>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
                                          <Typography>PRICE:</Typography>
                                        </td>
                                        <td>
                                          <Typography> $ {eitem.totalPrice}</Typography>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
                                          <Typography>SELLER:</Typography>
                                        </td>
                                        <td>
                                          <Typography> {eitem.product.seller.firstName} {eitem.product.seller.lastName}</Typography>
                                        </td>
                                      </tr>

                                      {/* <tr>
                                <td>
                                <Typography>PAYMENT STATUS:</Typography>
                                </td>
                                <td>
                                <Typography> {eitem.isPaymentDone}</Typography>
                                </td>
                              </tr> */}
                                    </tbody>
                                  </table>
                                </Grid>
                              </Grid>
                            </Card>  <br></br>
                          </>
                        ))
                      }

                    </Grid>


                  </Grid>





                </div>
              ) : ""
            }
          </Card><br></br>
          </>
        )
        )
      ) : <CircularProgress />
      }
      <br></br>
    </div>
  )
}
export default AdminOrders
