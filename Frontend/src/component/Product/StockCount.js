import { Chip } from '@mui/material';
import React from 'react'

function StockCount(props) {

    const value = props.value;

  if (value == 0) {
    return <Chip label="STOCK UNAVAILABLE" size='small' color='error' style={{fontSize: '0.7rem', fontFamily:'saira'}}></Chip>;
  } else if (value < 10) {
    return <Chip label={value + " in Stock"}size='small' color='warning' style={{fontSize: '0.7rem'}}></Chip>;
    // <p style={{color:'#c49c16'}}><b>Just {value} Quantity left</b></p>;
  } else {
    return <Chip label="STOCK AVAILABLE" size='small' color='success' style={{fontSize: '0.7rem', fontFamily:'saira'}}></Chip>;
  }

  return (
    <div>
      
    </div>
  )
}

export default StockCount
