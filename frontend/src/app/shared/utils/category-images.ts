export function getCategoryFallbackSvg(categoria?: string): string {
  const cat = (categoria || '').toLowerCase();

  if (cat.includes('cpu') || cat.includes('processador')) {
    return `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 200 200" fill="none">
      <rect x="30" y="30" width="140" height="140" rx="8" fill="#1a1a2e" stroke="#2563eb" stroke-width="2"/>
      <rect x="50" y="50" width="100" height="100" rx="4" fill="#16213e" stroke="#2563eb" stroke-width="1.5"/>
      <rect x="65" y="65" width="70" height="70" rx="3" fill="#0f3460" stroke="#2563eb" stroke-width="1"/>
      <rect x="75" y="75" width="50" height="50" rx="2" fill="#1a1a2e"/>
      <text x="100" y="105" text-anchor="middle" fill="#2563eb" font-size="10" font-weight="bold" font-family="monospace">CPU</text>
      <line x1="30" y1="50" x2="15" y2="50" stroke="#2563eb" stroke-width="2"/><line x1="30" y1="80" x2="15" y2="80" stroke="#2563eb" stroke-width="2"/>
      <line x1="30" y1="110" x2="15" y2="110" stroke="#2563eb" stroke-width="2"/><line x1="30" y1="140" x2="15" y2="140" stroke="#2563eb" stroke-width="2"/>
      <line x1="170" y1="50" x2="185" y2="50" stroke="#2563eb" stroke-width="2"/><line x1="170" y1="80" x2="185" y2="80" stroke="#2563eb" stroke-width="2"/>
      <line x1="170" y1="110" x2="185" y2="110" stroke="#2563eb" stroke-width="2"/><line x1="170" y1="140" x2="185" y2="140" stroke="#2563eb" stroke-width="2"/>
      <line x1="50" y1="30" x2="50" y2="15" stroke="#2563eb" stroke-width="2"/><line x1="80" y1="30" x2="80" y2="15" stroke="#2563eb" stroke-width="2"/>
      <line x1="110" y1="30" x2="110" y2="15" stroke="#2563eb" stroke-width="2"/><line x1="140" y1="30" x2="140" y2="15" stroke="#2563eb" stroke-width="2"/>
      <line x1="50" y1="170" x2="50" y2="185" stroke="#2563eb" stroke-width="2"/><line x1="80" y1="170" x2="80" y2="185" stroke="#2563eb" stroke-width="2"/>
      <line x1="110" y1="170" x2="110" y2="185" stroke="#2563eb" stroke-width="2"/><line x1="140" y1="170" x2="140" y2="185" stroke="#2563eb" stroke-width="2"/>
    </svg>`;
  }

  if (cat.includes('gpu') || cat.includes('video') || cat.includes('gráfica') || cat.includes('placa')) {
    return `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 200 200" fill="none">
      <rect x="15" y="55" width="170" height="90" rx="6" fill="#1a1a2e" stroke="#e8650a" stroke-width="2"/>
      <rect x="55" y="35" width="90" height="20" rx="3" fill="#1a1a2e" stroke="#e8650a" stroke-width="1.5"/>
      <rect x="70" y="68" width="60" height="35" rx="4" fill="#16213e" stroke="#e8650a" stroke-width="1"/>
      <rect x="75" y="73" width="50" height="25" rx="2" fill="#0f3460"/>
      <text x="100" y="90" text-anchor="middle" fill="#e8650a" font-size="8" font-weight="bold" font-family="monospace">GPU</text>
      <circle cx="40" cy="100" r="5" fill="#e8650a" opacity="0.6"/><circle cx="55" cy="100" r="5" fill="#e8650a" opacity="0.6"/>
      <circle cx="145" cy="100" r="5" fill="#e8650a" opacity="0.6"/><circle cx="160" cy="100" r="5" fill="#e8650a" opacity="0.6"/>
      <rect x="30" y="118" width="30" height="12" rx="2" fill="#16213e" stroke="#e8650a" stroke-width="1"/>
      <rect x="140" y="118" width="30" height="12" rx="2" fill="#16213e" stroke="#e8650a" stroke-width="1"/>
      <rect x="88" y="130" width="24" height="15" rx="2" fill="#0f3460" stroke="#e8650a" stroke-width="1"/>
    </svg>`;
  }

  if (cat.includes('ram') || cat.includes('memoria') || cat.includes('memória')) {
    return `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 200 200" fill="none">
      <rect x="25" y="20" width="150" height="160" rx="6" fill="#1a1a2e" stroke="#0f6e56" stroke-width="2"/>
      <rect x="35" y="30" width="130" height="20" rx="3" fill="#16213e" stroke="#0f6e56" stroke-width="1"/>
      <rect x="35" y="60" width="130" height="20" rx="3" fill="#16213e" stroke="#0f6e56" stroke-width="1"/>
      <rect x="35" y="90" width="130" height="20" rx="3" fill="#16213e" stroke="#0f6e56" stroke-width="1"/>
      <rect x="35" y="120" width="130" height="20" rx="3" fill="#16213e" stroke="#0f6e56" stroke-width="1"/>
      <rect x="38" y="33" width="40" height="14" rx="2" fill="#0f3460"/>
      <rect x="38" y="63" width="40" height="14" rx="2" fill="#0f3460"/>
      <rect x="38" y="93" width="40" height="14" rx="2" fill="#0f3460"/>
      <rect x="38" y="123" width="40" height="14" rx="2" fill="#0f3460"/>
      <text x="100" y="175" text-anchor="middle" fill="#0f6e56" font-size="10" font-weight="bold" font-family="monospace">RAM</text>
      <rect x="88" y="33" width="30" height="14" rx="2" fill="#0f6e56" opacity="0.4"/>
      <rect x="88" y="63" width="30" height="14" rx="2" fill="#0f6e56" opacity="0.4"/>
      <rect x="88" y="93" width="30" height="14" rx="2" fill="#0f6e56" opacity="0.4"/>
      <rect x="88" y="123" width="30" height="14" rx="2" fill="#0f6e56" opacity="0.4"/>
    </svg>`;
  }

  if (cat.includes('ssd') || cat.includes('armazenamento') || cat.includes('nvme') || cat.includes('hdd') || cat.includes('disco')) {
    return `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 200 200" fill="none">
      <rect x="20" y="50" width="160" height="100" rx="8" fill="#1a1a2e" stroke="#185fa5" stroke-width="2"/>
      <rect x="35" y="65" width="130" height="70" rx="4" fill="#16213e" stroke="#185fa5" stroke-width="1"/>
      <text x="100" y="108" text-anchor="middle" fill="#185fa5" font-size="14" font-weight="bold" font-family="monospace">SSD</text>
      <circle cx="55" cy="80" r="3" fill="#185fa5" opacity="0.5"/><circle cx="55" cy="90" r="3" fill="#185fa5" opacity="0.5"/>
      <circle cx="55" cy="100" r="3" fill="#185fa5" opacity="0.5"/><circle cx="55" cy="110" r="3" fill="#185fa5" opacity="0.5"/>
      <rect x="135" y="75" width="20" height="30" rx="2" fill="#0f3460" stroke="#185fa5" stroke-width="1"/>
      <rect x="30" y="155" width="40" height="6" rx="2" fill="#16213e" stroke="#185fa5" stroke-width="0.5"/>
      <rect x="80" y="155" width="60" height="6" rx="2" fill="#16213e" stroke="#185fa5" stroke-width="0.5"/>
      <rect x="148" y="155" width="25" height="6" rx="2" fill="#16213e" stroke="#185fa5" stroke-width="0.5"/>
    </svg>`;
  }

  // Fallback genérico
  return `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 200 200" fill="none">
    <rect x="30" y="30" width="140" height="140" rx="10" fill="#1a1a2e" stroke="#4a4a6a" stroke-width="2"/>
    <rect x="50" y="50" width="100" height="100" rx="6" fill="#16213e" stroke="#4a4a6a" stroke-width="1.5"/>
    <circle cx="100" cy="85" r="20" fill="#0f3460" stroke="#4a4a6a" stroke-width="1.5"/>
    <path d="M70 120 L100 95 L130 120Z" fill="#0f3460" stroke="#4a4a6a" stroke-width="1.5"/>
    <circle cx="100" cy="85" r="8" fill="#1a1a2e"/>
    <rect x="95" y="115" width="10" height="20" rx="2" fill="#0f3460" stroke="#4a4a6a" stroke-width="1"/>
  </svg>`;
}
