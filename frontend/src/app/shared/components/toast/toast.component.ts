import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="fixed bottom-6 right-6 z-[9999] flex flex-col gap-2">
      <div
        *ngFor="let toast of (toastService.toasts$ | async)"
        class="toast flex items-center gap-3 px-4 py-3 rounded min-w-[260px] shadow-xl text-sm font-medium cursor-pointer"
        [ngClass]="{
          'bg-green-800 border-l-4 border-green-400 text-white': toast.type === 'success',
          'bg-red-800 border-l-4 border-red-400 text-white': toast.type === 'error',
          'bg-blue-900 border-l-4 border-blue-400 text-white': toast.type === 'info'
        }"
        (click)="toastService.dismiss(toast.id)"
      >
        <svg *ngIf="toast.type === 'success'" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" class="flex-shrink-0"><polyline points="20 6 9 17 4 12"/></svg>
        <svg *ngIf="toast.type === 'error'" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" class="flex-shrink-0"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
        <svg *ngIf="toast.type === 'info'" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" class="flex-shrink-0"><circle cx="12" cy="12" r="10"/><line x1="12" y1="16" x2="12" y2="12"/><line x1="12" y1="8" x2="12.01" y2="8"/></svg>
        <span>{{ toast.message }}</span>
      </div>
    </div>
  `,
})
export class ToastComponent {
  constructor(public toastService: ToastService) {}
}
