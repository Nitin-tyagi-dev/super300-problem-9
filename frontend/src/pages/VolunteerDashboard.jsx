import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import api from "../services/api";
import { useAuthStore } from "../store/useAuthStore";

export default function VolunteerDashboard() {
  const user = useAuthStore((s) => s.user);
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(false);

  const loadTasks = async () => {
    setLoading(true);
    try {
      const { data } = await api.get("/tasks", { params: { userId: user?.id } });
      setTasks(data);
    } catch {
      toast.error("Failed to load tasks");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadTasks();
  }, []);

  const acceptTask = async (taskId) => {
    await api.patch(`/tasks/${taskId}/accept`, { userId: user?.id || "volunteer-demo" });
    toast.success("Task accepted");
    loadTasks();
  };

  const completeTask = async (taskId) => {
    await api.patch(`/tasks/${taskId}/complete`);
    toast.success("Task completed");
    loadTasks();
  };

  return (
    <div className="mx-auto max-w-5xl p-6">
      <h1 className="text-2xl font-semibold">Volunteer Dashboard</h1>
      <div className="mt-4 space-y-3">
        {loading ? <p>Loading tasks...</p> : tasks.map((task) => (
          <div key={task.id} className="rounded border border-slate-700 p-3">
            <div className="font-medium">{task.title}</div>
            <div className="text-sm text-slate-300">{task.description}</div>
            <div className="mt-2 flex gap-2 text-sm">
              <span className="rounded bg-slate-800 px-2 py-1">{task.status}</span>
              {task.status === "PENDING" && <button className="rounded bg-blue-600 px-2 py-1" onClick={() => acceptTask(task.id)}>Accept</button>}
              {task.status !== "COMPLETED" && <button className="rounded bg-emerald-600 px-2 py-1" onClick={() => completeTask(task.id)}>Complete</button>}
            </div>
          </div>
        ))}
        {!loading && !tasks.length && <p className="text-slate-300">No tasks assigned yet.</p>}
      </div>
    </div>
  );
}
