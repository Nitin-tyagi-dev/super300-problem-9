import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import api from "../../services/api";
import { roleHomeRoute, useAuthStore } from "../../store/useAuthStore";

export default function LoginPage() {
  const navigate = useNavigate();
  const login = useAuthStore((s) => s.login);
  const [phone, setPhone] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const onSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const { data } = await api.post("/auth/login", { phone, password });
      login({ user: data.user, token: data.token });
      toast.success("Logged in successfully");
      navigate(roleHomeRoute(data.user?.role), { replace: true });
    } catch (error) {
      toast.error(error?.response?.data?.message || "Login failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="mx-auto mt-10 max-w-md rounded-xl border border-slate-700 bg-slate-900/40 p-6">
      <h1 className="text-2xl font-semibold">Login</h1>
      <form className="mt-5 space-y-3" onSubmit={onSubmit}>
        <input className="w-full rounded bg-slate-800 p-2" placeholder="Phone" value={phone} onChange={(e) => setPhone(e.target.value)} required />
        <input className="w-full rounded bg-slate-800 p-2" placeholder="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        <button className="w-full rounded bg-blue-600 px-4 py-2 disabled:opacity-60" disabled={loading}>
          {loading ? "Signing in..." : "Login"}
        </button>
      </form>
      <p className="mt-4 text-sm text-slate-300">
        No account? <Link className="text-blue-400" to="/register">Register</Link>
      </p>
    </div>
  );
}
