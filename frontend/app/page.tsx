'use client'

import { useState } from 'react';

export default function Home() {
  const [inputText, setInputText] = useState<string>('');
  const [responseMessage, setResponseMessage] = useState<string>('');

  const handleSubmit = async (url: string) => {
    try {
      const response = await fetch(url, {
          method: 'GET',
      });


      if (response.ok) {
        const data = await response.json();
        const id = (data.id) ? data.id : "blunk id";
        setResponseMessage(`id: ${id}, value: ${data.value}`);
      } else {
        setResponseMessage('Error: Something went wrong');
      }
    } catch (error) {
      if (error instanceof Error) {
        console.error('Error message:', error.message);
        console.error('Error stack:', error.stack);
        setResponseMessage(`Error: ${error.message}`);
      } else {
        console.error('Unknown error:', error);
        setResponseMessage('Error: An unknown error occurred');
      }
    }
  }

  const handleSubmitHashId = async () => {
    handleSubmit(`http://localhost:8080/hash?id=${encodeURIComponent(inputText)}`)
  }
  const handleSubmitRandom = () => {
    handleSubmit(`http://localhost:8080/random`)
  }

  return (
    <div style={{ padding: '20px' }}>
      <h1>Send Text to Server</h1>
      <input
        type="text"
        value={inputText}
        onChange={(e) => setInputText(e.target.value)}
        placeholder="Enter text here"
        style={{ marginRight: '10px' }}
      />
      <button style={{ marginLeft: '5px' }} onClick={handleSubmitHashId}>Send</button>
      <button style={{ marginLeft: '5px' }} onClick={handleSubmitRandom}>Random</button>
      {responseMessage && (
        <div style={{ marginTop: '20px' }}>
          <strong>Response:</strong> {responseMessage}
        </div>
      )}
    </div>
  );
}
