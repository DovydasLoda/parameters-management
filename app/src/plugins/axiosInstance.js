const { default: Axios } = require("axios");

const axiosinstance = Axios.create({
    baseURL: "http://localhost:8080/api/",
});


export default axiosinstance;

