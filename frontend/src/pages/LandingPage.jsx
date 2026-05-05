import { Link } from "react-router-dom";

export default function LandingPage() {
  return (
    <div className="mx-auto max-w-6xl p-6">
      <h1>bndsbcsmndmch fiewbvmsdn  isefbesiuf isfbisduvsddv </h1>
      <h1 className="text-4xl font-bold">Community Disaster Response Network</h1>
      <p className="mt-2 text-xl text-slate-300">Faster Response. Stronger Communities.</p>
      <div className="mt-8 flex gap-3">
        <Link className="rounded bg-blue-600 px-4 py-2" to="/login">Login</Link>
        <Link className="rounded border border-slate-600 px-4 py-2" to="/register">Register</Link>
      </div>
    </div>
  );
}
