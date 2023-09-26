import { Box, Typography, CircularProgress } from "@mui/material";
import restrict from '../../images/restrict.png';
import { useNavigate } from 'react-router-dom';
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';
export default function InActive() {
    const navigate = useNavigate();
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
        backgroundColor: "#f5f5f5",
      }}
    >
         <p onClick={()=> navigate('/')}><KeyboardBackspaceIcon/>Go Back</p>
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          p: 4,
          backgroundColor: "white",
          borderRadius: 4,
          boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.1)",
        }}
      >

       

        <img
          src={restrict}
          alt="No access"
          width={280}
          height={280}
        />
        <Typography variant="h5" component="h1" align="center">
          Your account still needs to approved by ADMIN, Please wait for sometime
        </Typography>
        <Typography variant="body1" align="center" my={2}>
          We apologize for the inconvenience. Your account still needs to approved by ADMIN. Please check back later.
        </Typography>
        
      </Box>
    </Box>
  );
}