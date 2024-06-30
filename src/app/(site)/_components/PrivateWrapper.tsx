import React from 'react';

const PrivateWrapper = ({
  children,
}: {
  children: React.ReactNode;
}) => {
  return (
    <main className='h-screen w-full px-3'>
      {children}
    </main>
  );
};

export default PrivateWrapper;