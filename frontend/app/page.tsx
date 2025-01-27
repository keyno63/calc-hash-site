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
    <div className="p-5">
      <h1 className="text-2xl font-bold mb-4">Send Text to Server</h1>
      <input
        type="text"
        value={inputText}
        onChange={(e) => setInputText(e.target.value)}
        placeholder="Enter text here"
        className="border border-gray-300 p-2 rounded mr-2"
      />
      <button className="bg-blue-500 text-white p-2 rounded ml-2" onClick={handleSubmitHashId}>Send</button>
      <button className="bg-green-500 text-white p-2 rounded ml-2" onClick={handleSubmitRandom}>Random</button>
      {responseMessage && (
        <div className="mt-4">
          <strong>Response:</strong> {responseMessage}
        </div>
      )}
    </div>
  );
}