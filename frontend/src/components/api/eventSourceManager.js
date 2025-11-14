let eventSource = null;

export function connectSSE(url, onMessage, onOpen, onError) {
  if (eventSource) {
    console.log("Already connected SSE");
    return;
  }

  eventSource = new EventSource(url);

  eventSource.onopen = (e) => {
    console.log("SSE 연결됨");
    onOpen && onOpen(e);
  };

  eventSource.onmessage = (event) => {
    onMessage && onMessage(JSON.parse(event.data));
  };

  eventSource.onerror = (e) => {
    console.log("SSE 오류 발생, 재연결 시도...");
    onError && onError(e);

    // 연결 오류 시 close 후 null 처리
    eventSource.close();
    eventSource = null;

    // 재연결 시도 (3초 후)
    setTimeout(() => {
      connectSSE(url, onMessage, onOpen, onError);
    }, 3000);
  };

  return eventSource;
}

export function disconnectSSE() {
  if (eventSource) {
    eventSource.close();
    eventSource = null;
    console.log("SSE 연결 해제됨");
  }
}