import { useState } from "react";
import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import api from "../../services/api";

export default function RegisterPage() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    name: "",
    phone: "",
    email: "",
    password: "",
    role: "CITIZEN",
    district: "",
    state: "",
  });
  const [loading, setLoading] = useState(false);

  const onChange = (key, value) => setForm((s) => ({ ...s, [key]: value }));

  const onSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await api.post("/auth/register", form);
      toast.success("Registered successfully. Please login.");
      navigate("/login", { replace: true });
    } catch (error) {
      toast.error(error?.response?.data?.message || "Registration failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="mx-auto mt-8 max-w-lg rounded-xl border border-slate-700 bg-slate-900/40 p-6">
      <h1 className="text-2xl font-semibold">Register</h1>
      <form className="mt-5 grid gap-3" onSubmit={onSubmit}>
        <input className="rounded bg-slate-800 p-2" placeholder="Name" value={form.name} onChange={(e) => onChange("name", e.target.value)} required />
        <input className="rounded bg-slate-800 p-2" placeholder="Phone" value={form.phone} onChange={(e) => onChange("phone", e.target.value)} required />
        <input className="rounded bg-slate-800 p-2" placeholder="Email" type="email" value={form.email} onChange={(e) => onChange("email", e.target.value)} />
        <input className="rounded bg-slate-800 p-2" placeholder="Password" type="password" value={form.password} onChange={(e) => onChange("password", e.target.value)} required />
        <select className="rounded bg-slate-800 p-2" value={form.role} onChange={(e) => onChange("role", e.target.value)}>
          <option value="CITIZEN">Citizen</option>
          <option value="VOLUNTEER">Volunteer</option>
          <option value="AUTHORITY">Authority</option>
          <option value="ADMIN">Admin</option>
        </select>
        <input className="rounded bg-slate-800 p-2" placeholder="District" value={form.district} onChange={(e) => onChange("district", e.target.value)} required />
        <input className="rounded bg-slate-800 p-2" placeholder="State" value={form.state} onChange={(e) => onChange("state", e.target.value)} required />
        <button className="rounded bg-emerald-600 px-4 py-2 disabled:opacity-60" disabled={loading}>
          {loading ? "Creating account..." : "Register"}
        </button>
      </form>
    </div>
  );
}
