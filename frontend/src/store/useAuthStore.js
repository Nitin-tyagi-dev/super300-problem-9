import { create } from "zustand";
import { persist } from "zustand/middleware";
// jfnvienvnevneur
// nsvnunfvbueufnbfen
// kfnjdndabon
// jfivjnjfnv
export const useAuthStore = create(
  persist(
    (set) => ({
      user: null,
      token: null,
      isAuthenticated: false,
      isLoading: false,
      login: ({ user, token }) => set({ user, token, isAuthenticated: true }),
      logout: () => set({ user: null, token: null, isAuthenticated: false }),
      updateUser: (user) => set({ user }),
      setLoading: (isLoading) => set({ isLoading }),
    }),
    { name: "cdrn-auth" }
  )
);

export function roleHomeRoute(role) {
  switch (role) {
    case "CITIZEN":
      return "/citizen/dashboard";
    case "VOLUNTEER":
      return "/volunteer/dashboard";
    case "AUTHORITY":
      return "/authority/dashboard";
    case "ADMIN":
      return "/admin/dashboard";
    default:
      return "/";
  }
}
