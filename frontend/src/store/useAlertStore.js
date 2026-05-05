import { create } from "zustand";
import api from "../services/api";
// added jbdvbejbvijebrv
// vjenvoenobneonb1
// kvmibneubneiun
//vkmeonvienvenr
//vmoeinvenirubn
//vkmeonvineb
//bnenbuneuunbeun
//venbienibneinbienr
export const useAlertStore = create((set) => ({
  activeAlerts: [],
  latestAlert: null,
  isLoading: false,
  fetchActiveAlerts: async () => {
    set({ isLoading: true });
    try {
      const { data } = await api.get("/alerts/active");
      set({ activeAlerts: data, latestAlert: data[0] || null, isLoading: false });
    } catch {
      set({ isLoading: false });
    }
  },
  createAlert: async (payload) => {
    const { data } = await api.post("/alerts", payload);
    set((state) => ({
      activeAlerts: [data, ...state.activeAlerts],
      latestAlert: data,
    }));
    return data;
  },
  addAlert: (alert) =>
    set((state) => ({
      activeAlerts: [alert, ...state.activeAlerts],
      latestAlert: alert,
    })),
  dismissAlert: (id) =>
    set((state) => ({
      activeAlerts: state.activeAlerts.filter((a) => a.id !== id),
      latestAlert: state.latestAlert?.id === id ? null : state.latestAlert,
    })),
}));
