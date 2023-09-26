import axios from "axios";

  export const url =`http://localhost:8080/`;

  export const loginUser = (username, password) => {
    return axios.post(url+'user/signin', {
        username: username,
        password: password
      },{
        headers:{
          'Accept' : '*/*',
         'Content-Type': 'application/json',
         "Access-Control-Allow-Origin":"*",
         "Access-Control-Allow-Headers":"*"
      }
    })
  }

  
  

    const config = {};
    // {
    //     headers: {
    //       "Authorization": `Bearer ${localStorage.getItem('Token')}`
    //     }
    //   };

 
export const pendingAccount = () => {
    return axios.get(url+'user/allusers', config);
       
  };

  export const StatusApprove = (data) => {
    return axios.put(url+'user/status/'+localStorage.getItem('userId'),data ,config);
  };

 
  
  export const getAllProductApi = (searchValue) => {
    const api = url+'product/search?productName='+searchValue+'&userId='+localStorage.getItem('userId')
    
    return axios.get(api
      //, { headers: { "Authorization": `Bearer ${localStorage.getItem('Token')}` } });
      , {});
      

    
  }