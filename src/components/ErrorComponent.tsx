import React from 'react';

const ErrorComponent = ({ error }: { error: string }) => {
  return (
    <div className='border border-red-500 p-3 rounded-md w-max'>{error}</div>
  );
};

export default ErrorComponent;