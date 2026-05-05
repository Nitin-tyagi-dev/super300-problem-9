import { useEffect } from "react";
import { useAlertStore } from "../store/useAlertStore";

export default function AlertsPage() {
  const { activeAlerts, fetchActiveAlerts, isLoading } = useAlertStore();

  useEffect(() => {
    fetchActiveAlerts();
  }, [fetchActiveAlerts]);
    vkhjdvbk.ads cew cnrfguiw m,vmerio;vh,gqergvikuj4t 4cxmq efyvuc  wd mfvgfr8qeyu vdfbuyk fdsvghadiyvbaebnfv oadfyvadf vuyadf gvjkhadfd7ov nvaf vadfnvuy adfafnrveakv nadfvuya gdj,hfva dzy8oyv m,naefnv vuyaer gvjlhre vyudfvdfukv bjdfhv uafgv eavh,baefyfuv bsfb vesvsebvdsfukbv ydsf vbdsfbv8dsfbv dsugy vdsfhjv uydfv hvibidk, bdhjl vblhdfv lfvh y

  return (
    <div className="mx-auto max-w-5xl p-6">
      <h1 className="text-2xl font-semibold">Alerts</h1>
      <div className="mt-4 space-y-2">
        {isLoading ? <p>Loading alerts...</p> : activeAlerts.map((alert) => (
          <div key={alert.id} className="rounded border border-slate-700 p-3">
            <div className="font-medium">{alert.title} <span className="text-xs text-slate-400">({alert.severity})</span></div>
            <div className="text-sm text-slate-300">{alert.message}</div>
          </div>
        ))}
        {!isLoading && !activeAlerts.length && <p className="text-slate-300">No active alerts found.</p>}
      </div>
    </div>
  );
}
