import PrivateWrapper from "./_components/PrivateWrapper";

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <PrivateWrapper>
      {children}
    </PrivateWrapper>
  );
}
