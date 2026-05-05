import { Navigate, Route, Routes } from "react-router-dom";
import ProtectedRoute from "./components/auth/ProtectedRoute";
import LandingPage from "./pages/LandingPage";
import LoginPage from "./pages/auth/LoginPage";
import RegisterPage from "./pages/auth/RegisterPage";
import OtpPage from "./pages/auth/OtpPage";
import PlaceholderPage from "./pages/PlaceholderPage";
import CitizenDashboard from "./pages/CitizenDashboard";
import VolunteerDashboard from "./pages/VolunteerDashboard";
import AuthorityDashboard from "./pages/AuthorityDashboard";
import AlertsPage from "./pages/AlertsPage";
import ProfilePage from "./pages/ProfilePage";
import NotificationsPage from "./pages/NotificationsPage";

const P = (title) => <PlaceholderPage title={title} />;

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/otp" element={<OtpPage />} />
      <Route path="/alerts/public" element={P("Public Alerts")} />

      <Route element={<ProtectedRoute roles={["CITIZEN"]} />}>
        <Route path="/citizen/dashboard" element={<CitizenDashboard />} />
        <Route path="/citizen/report" element={<CitizenDashboard />} />
        <Route path="/citizen/sos" element={<CitizenDashboard />} />
        <Route path="/citizen/nearby" element={<CitizenDashboard />} />
        <Route path="/citizen/shelters" element={<CitizenDashboard />} />
        <Route path="/citizen/damage-report" element={<CitizenDashboard />} />
        <Route path="/citizen/my-reports" element={<CitizenDashboard />} />
      </Route>

      <Route element={<ProtectedRoute roles={["VOLUNTEER"]} />}>
        <Route path="/volunteer/dashboard" element={<VolunteerDashboard />} />
        <Route path="/volunteer/tasks" element={<VolunteerDashboard />} />
        <Route path="/volunteer/tasks/:id" element={<VolunteerDashboard />} />
        <Route path="/volunteer/register" element={P("Volunteer Register")} />
        <Route path="/volunteer/map" element={<VolunteerDashboard />} />
      </Route>

      <Route element={<ProtectedRoute roles={["AUTHORITY"]} />}>
        <Route path="/authority/dashboard" element={<AuthorityDashboard />} />
        <Route path="/authority/incidents" element={<AuthorityDashboard />} />
        <Route path="/authority/map" element={<AuthorityDashboard />} />
        <Route path="/authority/volunteers" element={<AuthorityDashboard />} />
        <Route path="/authority/resources" element={<AuthorityDashboard />} />
        <Route path="/authority/shelters" element={<AuthorityDashboard />} />
        <Route path="/authority/alerts" element={<AuthorityDashboard />} />
        <Route path="/authority/damage-reports" element={<AuthorityDashboard />} />
        <Route path="/authority/tasks" element={<AuthorityDashboard />} />
      </Route>

      <Route element={<ProtectedRoute roles={["ADMIN"]} />}>
        <Route path="/admin/dashboard" element={P("Admin Dashboard")} />
        <Route path="/admin/users" element={P("User Management")} />
        <Route path="/admin/stats" element={P("System Stats")} />
        <Route path="/admin/alerts" element={P("Admin Alerts")} />
      </Route>

      <Route element={<ProtectedRoute roles={["CITIZEN", "VOLUNTEER", "AUTHORITY", "ADMIN"]} />}>
        <Route path="/notifications" element={<NotificationsPage />} />
        <Route path="/profile" element={<ProfilePage />} />
        <Route path="/alerts" element={<AlertsPage />} />
      </Route>

      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}
