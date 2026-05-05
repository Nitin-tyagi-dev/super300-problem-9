import { Navigate, Outlet } from "react-router-dom";
import { useAuthStore } from "../../store/useAuthStore";

export default function ProtectedRoute({ roles = [] }) {
  const { isAuthenticated, user } = useAuthStore();

  if (!isAuthenticated) return <Navigate to="/login" replace />;
  if (roles.length && !roles.includes(user?.role)) return <Navigate to="/" replace />;
  return <Outlet />;
}
