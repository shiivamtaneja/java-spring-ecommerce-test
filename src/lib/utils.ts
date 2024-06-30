import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export function convertTimestampToDateString(timestamp: string) {
  const date = new Date(timestamp);

  return date.toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' });
}