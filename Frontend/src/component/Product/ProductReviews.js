
import ThumbUpAlt from '@mui/icons-material/ThumbUpAlt';
import { FormControl, Grid, Rating, Snackbar, TextField, Typography } from '@mui/material';
import Button from '@mui/material/Button';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import * as React from 'react';
import axios from 'axios';
import { url } from '../../ServiceApi/ServiceApi';
export default function ProductReviews({ addCategoryModal, productId }) {
    const [openSnack, setOpenSnack] = React.useState(false);
    const [snackMessage, setSnackMessage] = React.useState('');
    const [isProfileOpen, setIsProfileOpen] = React.useState(false);
  const [fileName, setFileName] = React.useState("");
  const [review, setReview] = React.useState("");
  const [rate, setRate] = React.useState(0);
const handleSnackClose = () => {
    setOpenSnack(!openSnack);
};

function saveRating(rating){
    alert(productId+"===" + rating.target.value);
    setRate(rating.target.value);
    let data = {
      "productId": productId,
      "userId":localStorage.getItem('userId'),
      "rating": rating.target.value
    }
  
    axios.post(url+'product/ratings', data
        //, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
        , {})
        .then(res => {
          });
  }
  
  function saveReview(){
    alert(productId+"===" + review);
    if(review!==""){
      let data = {
        "productId": productId,
        "userId":localStorage.getItem('userId'),
        "message": review
      }
    
      axios.post(url+'product/reviews', data
         // , { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } })
         , {}) 
         .then(res => {
            setSnackMessage("Reviews added successfully");
            setOpenSnack(true);
            addCategoryModal();
            });
    
    } else {
        setSnackMessage("Reviews cannot be blank");
        setOpenSnack(true);
    }
    
  
  }

function approveApplicationSubmit(){
    saveReview()
}

  return (
    <React.Fragment>
      <div>
        <DialogContent>
        
        <Typography fontSize={'1.7rem'} color={"orange"}>
            
            {/* <Rating color="red" size='large' name="half-rating"
             onChange={(e)=> saveRating(e)} defaultValue={rate} precision={0.5} /> */}
              </Typography>
              <br></br>
              <FormControl variant="outlined" fullWidth sx={{ m: 1 }}>
                <TextField
                  id="outlined-multiline-flexible"
                  label="Add your reviews:"
                  multiline
                  fullWidth
                  value={review}
                  onChange={(e) => setReview(e.target.value)}
                  rows={4}
                />
              </FormControl> 
        </DialogContent>
        <DialogActions align='right'>
            <Button variant="contained" color="primary" 
            onClick={approveApplicationSubmit} >SUBMIT</Button>
        </DialogActions>

        <Snackbar
          style={{ whiteSpace: 'pre-wrap', width: '300px', top: '50%', bottom: '50%', left: '40%', right: '50%' }}
          autoHideDuration={3000}
          anchorOrigin={{
            vertical: "center",
            horizontal: "center"
          }}
          open={openSnack}
          onClose={handleSnackClose}
          message={snackMessage}
        />



      </div>
    </React.Fragment>
  );
}