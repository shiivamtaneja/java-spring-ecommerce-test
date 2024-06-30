'use client';

import { useEffect, useRef, useState } from "react";

const useQuery = <T, G>({
  queryFn,
  options,
  errMsg,
}: {
  queryFn: string,
  options: RequestInit,
  errMsg: string,
}) => {
  const [isLoading, setIsLoading] = useState(true);
  const [errorMsg, setErrorMsg] = useState<string | null>(null);
  const [data, setData] = useState<{
    message: string,
    data: T | null,
    other: G | null
  }>({
    message: '',
    data: null,
    other: null
  });

  const controllerRef = useRef<AbortController>();

  const getData = async (isRefetch = false) => {
    if (!isRefetch) {
      setIsLoading(true);
    }

    setErrorMsg(null);

    controllerRef.current = new AbortController();
    const signal = controllerRef.current.signal;

    try {
      const response = await fetch(queryFn, {
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include',
        ...options,
        signal
      });

      if (!response.ok) {
        const error = await response.json();
        throw new Error(error.error);
      }

      const data = await response.json();
      setData(data);

    } catch (error) {
      if (error instanceof Error) {
        if (error.message != 'The user aborted a request.') {
          setErrorMsg(`${errMsg}: ${error.message}`);
        }
      } else {
        console.error(error);
        setErrorMsg(`${errMsg}: Unkown error`);
      }
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    getData();

    return () => {
      if (controllerRef.current) {
        controllerRef.current.abort();
      }
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return {
    data,
    isLoading,
    errorMsg,
    refetch: () => getData(true)
  };
};

export default useQuery;