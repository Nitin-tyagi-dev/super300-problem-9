import { useState } from "react";
import toast from "react-hot-toast";
import api from "../../services/api";

export default function OtpPage() {
  const [phone, setPhone] = useState("");
  const [otp, setOtp] = useState("");
  const [loading, setLoading] = useState(false);

  const sendOtp = async () => {
    if (!phone) return toast.error("Phone is required");
    setLoading(true);
    try {
      const { data } = await api.post("/auth/send-otp", { phone });
      toast.success(`OTP sent${data.devOtp ? ` (dev: ${data.devOtp})` : ""}`);
    } catch (error) {
      toast.error(error?.response?.data?.message || "Failed to send OTP");
    } finally {
      setLoading(false);
    }
  };

  const verifyOtp = async () => {
    if (!phone || !otp) return toast.error("Phone and OTP are required");
    setLoading(true);
    try {
      await api.post("/auth/verify-otp", { phone, otp });
      toast.success("OTP verified");
    } catch (error) {
      toast.error(error?.response?.data?.message || "OTP verification failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="mx-auto mt-10 max-w-md rounded-xl border border-slate-700 bg-slate-900/40 p-6">
      <h1 className="text-2xl font-semibold">OTP Verification</h1>
      <div className="mt-4 space-y-3">
        <input className="w-full rounded bg-slate-800 p-2" placeholder="Phone" value={phone} onChange={(e) => setPhone(e.target.value)} />
        <div className="flex gap-2">
          <button className="rounded bg-blue-600 px-4 py-2 disabled:opacity-60" onClick={sendOtp} disabled={loading}>Send OTP</button>
          <button className="rounded bg-emerald-600 px-4 py-2 disabled:opacity-60" onClick={verifyOtp} disabled={loading}>Verify OTP</button>
        </div>
        <input className="w-full rounded bg-slate-800 p-2" placeholder="Enter OTP" value={otp} onChange={(e) => setOtp(e.target.value)} />
      </div>
    </div>
  );
}
