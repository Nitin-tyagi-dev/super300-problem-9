import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import api from "../services/api";
import { useIncidentStore } from "../store/useIncidentStore";
import { useAlertStore } from "../store/useAlertStore";
import { useAuthStore } from "../store/useAuthStore";

export default function AuthorityDashboard() {
  const user = useAuthStore((s) => s.user);
  const { incidents, fetchIncidents } = useIncidentStore();
  const { createAlert } = useAlertStore();
  const [taskForm, setTaskForm] = useState({ title: "", description: "", type: "RESCUE" });
  const [alertForm, setAlertForm] = useState({ title: "", message: "", type: "ADVISORY", severity: "YELLOW", disasterType: "FLOOD" });

  useEffect(() => {
    fetchIncidents();
  }, [fetchIncidents]);

  const createTask = async (e) => {
    e.preventDefault();
    try {
      await api.post("/tasks", {
        ...taskForm,
        assignedByAuthorityId: user?.id || "authority-demo",
        status: "PENDING",
        priority: 2,
      });
      setTaskForm({ title: "", description: "", type: "RESCUE" });
      toast.success("Task created");
    } catch {
      toast.error("Failed to create task");
    }
  };

  const publishAlert = async (e) => {
    e.preventDefault();
    try {
      await createAlert({
        ...alertForm,
        issuedByAuthorityId: user?.id || "authority-demo",
        isBroadcast: true,
        targetDistricts: [user?.district || "Unknown"],
        targetStates: [user?.state || "Unknown"],
      });
      setAlertForm({ title: "", message: "", type: "ADVISORY", severity: "YELLOW", disasterType: "FLOOD" });
      toast.success("Alert published");
    } catch {
      toast.error("Failed to publish alert");
    }
  };

  return (
    <div className="mx-auto max-w-6xl p-6">
      <h1 className="text-2xl font-semibold">Authority Dashboard</h1>
      <div className="mt-5 grid gap-6 md:grid-cols-2">
        <form className="space-y-2 rounded border border-slate-700 p-4" onSubmit={publishAlert}>
          <h2 className="font-semibold">Publish Alert</h2>
          <input className="w-full rounded bg-slate-800 p-2" placeholder="Title" value={alertForm.title} onChange={(e) => setAlertForm((s) => ({ ...s, title: e.target.value }))} required />
          <textarea className="w-full rounded bg-slate-800 p-2" placeholder="Message" value={alertForm.message} onChange={(e) => setAlertForm((s) => ({ ...s, message: e.target.value }))} required />
          <div className="grid grid-cols-3 gap-2">
            <select className="rounded bg-slate-800 p-2" value={alertForm.type} onChange={(e) => setAlertForm((s) => ({ ...s, type: e.target.value }))}>{["EARLY_WARNING", "EVACUATION", "SHELTER_INFO", "ROAD_CLOSURE", "ALL_CLEAR", "ADVISORY"].map((v) => <option key={v}>{v}</option>)}</select>
            <select className="rounded bg-slate-800 p-2" value={alertForm.disasterType} onChange={(e) => setAlertForm((s) => ({ ...s, disasterType: e.target.value }))}>{["FLOOD", "EARTHQUAKE", "CYCLONE", "FIRE", "LANDSLIDE", "TSUNAMI", "OTHER"].map((v) => <option key={v}>{v}</option>)}</select>
            <select className="rounded bg-slate-800 p-2" value={alertForm.severity} onChange={(e) => setAlertForm((s) => ({ ...s, severity: e.target.value }))}>{["GREEN", "YELLOW", "ORANGE", "RED"].map((v) => <option key={v}>{v}</option>)}</select>
          </div>
          <button className="rounded bg-red-600 px-3 py-2">Publish Alert</button>
        </form>

        <form className="space-y-2 rounded border border-slate-700 p-4" onSubmit={createTask}>
          <h2 className="font-semibold">Create Volunteer Task</h2>
          <input className="w-full rounded bg-slate-800 p-2" placeholder="Task title" value={taskForm.title} onChange={(e) => setTaskForm((s) => ({ ...s, title: e.target.value }))} required />
          <textarea className="w-full rounded bg-slate-800 p-2" placeholder="Task description" value={taskForm.description} onChange={(e) => setTaskForm((s) => ({ ...s, description: e.target.value }))} required />
          <select className="w-full rounded bg-slate-800 p-2" value={taskForm.type} onChange={(e) => setTaskForm((s) => ({ ...s, type: e.target.value }))}>
            {["RESCUE", "MEDICAL_AID", "FOOD_DISTRIBUTION", "SEARCH", "EVACUATION", "ASSESSMENT", "LOGISTICS"].map((v) => <option key={v}>{v}</option>)}
          </select>
          <button className="rounded bg-blue-600 px-3 py-2">Create Task</button>
        </form>
      </div>

      <section className="mt-6 rounded border border-slate-700 p-4">
        <h2 className="font-semibold">Recent Incidents</h2>
        <div className="mt-2 grid gap-2 md:grid-cols-2">
          {incidents.slice(0, 8).map((incident) => (
            <div key={incident.id} className="rounded bg-slate-800 p-2 text-sm">
              <div>{incident.type} - {incident.status}</div>
              <div className="text-slate-300">{incident.description}</div>
            </div>
          ))}
          {!incidents.length && <p className="text-slate-300">No incidents reported yet.</p>}
        </div>
      </section>
    </div>
  );
}
