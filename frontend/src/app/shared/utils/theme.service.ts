import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private darkModeSubject = new BehaviorSubject<boolean>(false);
  isDarkMode$: Observable<boolean> = this.darkModeSubject.asObservable();

  constructor() {}

  initializeTheme() {
    if (typeof window === 'undefined') return;
    const savedTheme = localStorage.getItem('theme');
    const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
    const isDark = savedTheme === 'dark' || (!savedTheme && prefersDark);

    if (isDark) {
      document.documentElement.classList.add('dark');
    }

    this.darkModeSubject.next(isDark);
  }

  toggleDarkMode() {
    if (typeof window === 'undefined') return;
    const isDark = this.darkModeSubject.value;
    const newTheme = !isDark;

    if (newTheme) {
      document.documentElement.classList.add('dark');
    } else {
      document.documentElement.classList.remove('dark');
    }

    localStorage.setItem('theme', newTheme ? 'dark' : 'light');
    this.darkModeSubject.next(newTheme);
  }
}
