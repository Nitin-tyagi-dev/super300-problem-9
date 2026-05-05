import axios from "axios";
import { toast } from "react-hot-toast";
import { useAuthStore } from "../store/useAuthStore";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api/v1",
});

api.interceptors.request.use((config) => {
  const token = useAuthStore.getState().token;
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error?.response?.status === 401) {
      useAuthStore.getState().logout();
      window.location.href = "/login";
    }
    if (error?.response?.status === 403) {
      toast.error("You are not authorized for this action.");
    }
    return Promise.reject(error);
  }
);

export default api;
