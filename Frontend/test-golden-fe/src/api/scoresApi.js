import axiosClient from "./axiosClient";

const scoreApi = {

  getScoresByRegistrationNumber: (registrationNumber) =>
    axiosClient.get(`/api/checkScore/${registrationNumber}`),

  getReportBySubject: (subject) =>
    axiosClient.get(`/api/report/${encodeURIComponent(subject)}`),

  getTopGroupA: () =>
    axiosClient.get("/api/topA"),
};

export default scoreApi;