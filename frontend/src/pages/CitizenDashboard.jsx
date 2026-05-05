import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import { useIncidentStore } from "../store/useIncidentStore";
import { useAuthStore } from "../store/useAuthStore";
import { useAlertStore } from "../store/useAlertStore";

export default function CitizenDashboard() {
  const user = useAuthStore((s) => s.user);
  const { incidents, fetchIncidents, createIncident, isLoading } = useIncidentStore();
  const { activeAlerts, fetchActiveAlerts } = useAlertStore();
  const [form, setForm] = useState({
    type: "FLOOD",
    severity: "MEDIUM",
    description: "",
    district: user?.district || "",
    state: user?.state || "",
    isSOS: false,
    affectedPersonsCount: 1,
  });

  useEffect(() => {
    fetchIncidents();
    fetchActiveAlerts();
  }, [fetchIncidents, fetchActiveAlerts]);

  const submitIncident = async (e) => {
    e.preventDefault();
    try {
      await createIncident({
        ...form,
        reportedByUserId: user?.id || "demo-user",
        location: { lat: 0, lng: 0, address: "Unknown" },
      });
      setForm((s) => ({ ...s, description: "" }));
      toast.success("Incident submitted");
    } catch (error) {
      toast.error(error?.response?.data?.message || "Failed to submit incident");
    }
  };

  return (
    <div className="mx-auto max-w-6xl p-6">
      <h1 className="text-2xl font-semibold">Citizen Dashboard</h1>
      <div className="mt-5 grid gap-6 md:grid-cols-2">
        <form className="space-y-2 rounded border border-slate-700 p-4" onSubmit={submitIncident}>
          <h2 className="font-semibold">Report Incident</h2>
          <select className="w-full rounded bg-slate-800 p-2" value={form.type} onChange={(e) => setForm((s) => ({ ...s, type: e.target.value }))}>
            {["FLOOD", "EARTHQUAKE", "CYCLONE", "FIRE", "LANDSLIDE", "MEDICAL", "MISSING_PERSON", "ROAD_BLOCKED", "OTHER"].map((t) => <option key={t}>{t}</option>)}
          </select>
          <select className="w-full rounded bg-slate-800 p-2" value={form.severity} onChange={(e) => setForm((s) => ({ ...s, severity: e.target.value }))}>
            {["LOW", "MEDIUM", "HIGH", "CRITICAL"].map((s) => <option key={s}>{s}</option>)}
          </select>
          <textarea className="w-full rounded bg-slate-800 p-2" placeholder="Describe the incident" value={form.description} onChange={(e) => setForm((s) => ({ ...s, description: e.target.value }))} required />
          <label className="flex items-center gap-2 text-sm">
            <input type="checkbox" checked={form.isSOS} onChange={(e) => setForm((s) => ({ ...s, isSOS: e.target.checked }))} />
            Mark as SOS
          </label>
          <button className="rounded bg-red-600 px-3 py-2">Submit</button>
        </form>

        <div className="space-y-4"> // added citizen dashboard 
          <section className="rounded border border-slate-700 p-4">
            <h2 className="font-semibold">Active Alerts</h2>
            <div className="mt-2 space-y-2">
              {activeAlerts.slice(0, 4).map((a) => (
                <div key={a.id} className="rounded bg-slate-800 p-2">
                  <div className="font-medium">{a.title}</div>
                  <div className="text-sm text-slate-300">{a.message}</div>
                </div>
              ))}
              {!activeAlerts.length && <p className="text-sm text-slate-300">No active alerts.</p>}
            </div>
          </section>
          <section className="rounded border border-slate-700 p-4">
            <h2 className="font-semibold">Recent Incidents</h2>
            <div className="mt-2 space-y-2">
              {isLoading ? <p>Loading...</p> : incidents.slice(0, 5).map((i) => (
                <div key={i.id} className="rounded bg-slate-800 p-2 text-sm">
                  <div>{i.type} - {i.severity}</div>
                  <div className="text-slate-300">{i.description}</div>
                </div>
              ))}
            </div>
          </section>
        </div>
      </div>
    </div>
  );
}
