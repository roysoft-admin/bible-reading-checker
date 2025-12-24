import { ChevronLeft, Check, Circle } from 'lucide-react';

interface ReadingPlanScreenProps {
  onNavigate: (screen: 'home' | 'plan' | 'progress' | 'settings') => void;
}

export default function ReadingPlanScreen({ onNavigate }: ReadingPlanScreenProps) {
  const readings = [
    { day: 1, passage: 'Genesis 1–3', completed: true },
    { day: 2, passage: 'Genesis 4–6', completed: true },
    { day: 3, passage: 'Genesis 7–9', completed: true },
    { day: 4, passage: 'Genesis 10–12', completed: true },
    { day: 5, passage: 'Genesis 13–15', completed: true },
    { day: 6, passage: 'Genesis 16–18', completed: true },
    { day: 7, passage: 'Genesis 19–21', completed: true },
    { day: 8, passage: 'Genesis 22–24', completed: true },
    { day: 9, passage: 'Genesis 25–27', completed: true },
    { day: 10, passage: 'Genesis 28–30', completed: true },
    { day: 11, passage: 'Genesis 31–33', completed: true },
    { day: 12, passage: 'Genesis 34–36', completed: true, today: true },
    { day: 13, passage: 'Genesis 37–39', completed: false },
    { day: 14, passage: 'Genesis 40–42', completed: false },
    { day: 15, passage: 'Genesis 43–45', completed: false },
    { day: 16, passage: 'Genesis 46–48', completed: false },
    { day: 17, passage: 'Genesis 49–50', completed: false },
    { day: 18, passage: 'Exodus 1–3', completed: false },
    { day: 19, passage: 'Exodus 4–6', completed: false },
    { day: 20, passage: 'Exodus 7–9', completed: false },
  ];

  return (
    <div className="h-full flex flex-col bg-gradient-to-b from-[#faf8f5] to-[#f5f1e8]">
      {/* Header */}
      <div className="px-6 pt-14 pb-6 bg-white/50 backdrop-blur-sm border-b border-[#e8e4db]/50">
        <button
          onClick={() => onNavigate('home')}
          className="mb-4 text-[#6b7b68] flex items-center gap-2 hover:text-[#4a5548] transition-colors"
        >
          <ChevronLeft className="w-5 h-5" />
          <span className="text-sm">Back</span>
        </button>
        <h2 className="text-[#4a5548]">Reading Plan</h2>
        <p className="text-sm text-[#8a9488] mt-1">1 Year Bible Reading Plan</p>
      </div>

      {/* Reading List */}
      <div className="flex-1 px-6 py-6 overflow-auto">
        <div className="space-y-2">
          {readings.map((reading) => (
            <div
              key={reading.day}
              className={`flex items-center gap-4 p-4 rounded-2xl transition-all ${
                reading.today
                  ? 'bg-[#a5b4a1]/10 border-2 border-[#a5b4a1]/30'
                  : reading.completed
                  ? 'bg-white/40 backdrop-blur-sm border border-[#e8e4db]/30'
                  : 'bg-white/60 backdrop-blur-sm border border-[#e8e4db]/50'
              }`}
            >
              <div className="flex-shrink-0">
                {reading.completed ? (
                  <div className="w-8 h-8 rounded-full bg-[#a5b4a1]/20 flex items-center justify-center">
                    <Check className="w-4 h-4 text-[#6b7b68]" strokeWidth={2.5} />
                  </div>
                ) : (
                  <div className="w-8 h-8 rounded-full border-2 border-[#e8e4db] flex items-center justify-center">
                    <Circle className="w-4 h-4 text-[#c5c9c3]" />
                  </div>
                )}
              </div>
              
              <div className="flex-1">
                <p className="text-sm text-[#8a9488] mb-1">Day {reading.day}</p>
                <h4 className={reading.completed ? 'text-[#8a9488]' : 'text-[#4a5548]'}>
                  {reading.passage}
                </h4>
              </div>

              {reading.today && (
                <div className="flex-shrink-0">
                  <span className="text-xs text-[#a5b4a1] bg-[#a5b4a1]/10 px-3 py-1 rounded-full">
                    Today
                  </span>
                </div>
              )}
            </div>
          ))}
        </div>
      </div>

      {/* Banner Ad Space (Free Version) */}
      <div className="bg-white/40 backdrop-blur-sm border-t border-[#e8e4db]/50 px-6 py-4">
        <div className="bg-gradient-to-r from-[#e8e4db]/30 to-[#d4a574]/20 rounded-2xl p-4 flex items-center justify-center">
          <p className="text-xs text-[#8a9488]">Ad space · Upgrade to remove ads</p>
        </div>
      </div>
    </div>
  );
}
