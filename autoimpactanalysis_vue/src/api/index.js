import axios from "../utils/http"
import store from "../store"

/* export function fetchPermission(){
    return axios.get("/api/permission?user=" + store.state.UserToken);
}

export function login(user){
    return axios.get("/api/login?user=" + user)
} */

export function fetchPermission() {
    return axios.get("/user/permission");
} 

export function login(user) {
    return axios.post("/user/login", user);
}