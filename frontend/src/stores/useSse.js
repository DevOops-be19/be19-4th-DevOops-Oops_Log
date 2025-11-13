import { defineStore } from "pinia";

let eventSource = null;
let reconnectTimer = null;

export const useSseStore = defineStore("sse", {
  state: () => ({
    isConnected: false,
    messages: [],       // 서버에서 받은 데이터
    lastEventId: null,  // 최근 이벤트 ID (옵션)
  }),

  persist: {
    key: "sse-store",   // 저장 키 이름
    storage: sessionStorage, // 또는 sessionStorage
    paths: ["messages", "lastEventId"], 
  },

  actions: {
    connect(url) {
      if (eventSource) {
        console.log("Already connected SSE");
        return;
      }

      console.log("SSE Connecting:", url);

      eventSource = new EventSource(url);

      eventSource.onopen = () => {
        console.log("SSE Connected");
        this.isConnected = true;
      };

    //   eventSource.onmessage = (event) => {
    //     if (event.data === "keep-alive") return;

    //     try {
    //     console.log(event.data);
    //       const json = JSON.parse(event.data);
    //       this.messages.push(json);
    //     } catch (e) {
    //       console.warn("JSON parse failed:", event.data);
    //     }

    //     if (event.lastEventId) {
    //       this.lastEventId = event.lastEventId;
    //     }
    //   };

      eventSource.onerror = () => {
        console.log("SSE Error → reconnecting...");
        this.isConnected = false;

        if (eventSource) {
          eventSource.close();
          eventSource = null;
        }

        if (!reconnectTimer) {
          reconnectTimer = setTimeout(() => {
            this.connect(url);
            reconnectTimer = null;
          }, 3000);
        }
      };
      return eventSource;
    },

    disconnect() {
      if (eventSource) {
        eventSource.close();
        eventSource = null;
      }
      this.isConnected = false;
    },

    clearMessages() {
      this.messages = [];
    }
  },
});