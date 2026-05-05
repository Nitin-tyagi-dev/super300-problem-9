import { useAuthStore } from "../store/useAuthStore";

export default function ProfilePage() {
  const user = useAuthStore((s) => s.user);

  return (
    <div className="mx-auto max-w-3xl p-6">
      <h1 className="text-2xl font-semibold">Profile</h1>
      <div className="mt-4 rounded border border-slate-700 p-4">
        <p>jb jhb  hdbvv dfvbdf dfivbdf ibvdf v if dfjjbdf jkbfdmvj dfm.vn,bvfm </p>
        <p><strong>Name:</strong> {user?.name || "-"}</p>
        <p><strong>Phone:</strong> {user?.phone || "-"}</p>
        <p><strong>Email:</strong> {user?.email || "-"}</p>
        <p><strong>Role:</strong> {user?.role || "-"}</p>
        <p><strong>District:</strong> {user?.district || "-"}</p>
        <p><strong>State:</strong> {user?.state || "-"}</p>
      </div>
    </div>
  );
}
