'use client'

import React, { useState } from "react";

interface PopConfirmProps {
  children: React.ReactElement;
  onConfirm: () => void;
  onCancel?: () => void;
  message: string;
}

const PopConfirm: React.FC<PopConfirmProps> = ({
  children,
  onConfirm,
  onCancel,
  message,
}) => {
  const [isVisible, setIsVisible] = useState(false);

  const handleConfirm = () => {
    setIsVisible(false);
    onConfirm();
  };

  const handleCancel = () => {
    setIsVisible(false);
    if (onCancel) {
      onCancel();
    }
  };

  return (
    <>
      {React.cloneElement(children, { onClick: () => setIsVisible(true),  })}

      {isVisible && (
        <div className="z-10 absolute bg-white border p-3 rounded shadow-lg">
          <p className="text-sm font-sans text-gray-primary">{message}</p>
          <div className="flex justify-end space-x-2 mt-3">
            <button
              className="bg-blue-primary text-white px-3 py-1 text-xs rounded"
              onClick={handleConfirm}
            >
              确认
            </button>
            <button
              className="bg-gray-400 text-white px-3 py-1 text-xs rounded"
              onClick={handleCancel}
            >
              取消
            </button>
          </div>
        </div>
      )}
    </>
  );
};

export default PopConfirm;
