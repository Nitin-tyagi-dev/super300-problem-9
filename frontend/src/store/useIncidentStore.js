import { create } from "zustand";
import api from "../services/api";

export const useIncidentStore = create((set) => ({
  incidents: [],
  isLoading: false,
  error: null,
  fetchIncidents: async () => {
    set({ isLoading: true, error: null });
    try {
      const { data } = await api.get("/incidents");
      set({ incidents: data, isLoading: false });
    } catch (error) {
      set({ error: error?.response?.data?.message || "Failed to load incidents", isLoading: false });
    }
  },
  createIncident: async (payload) => {
    const { data } = await api.post("/incidents", payload);
    set((s) => ({ incidents: [data, ...s.incidents] }));
    return data;
  },
  updateIncident: (incident) =>
    set((s) => ({
      incidents: s.incidents.map((i) => (i.id === incident.id ? incident : i)),
    })),
}));
