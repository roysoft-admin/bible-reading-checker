import { ChevronLeft, BookOpen, Calendar, TrendingUp } from 'lucide-react';

interface ProgressScreenProps {
  onNavigate: (screen: 'home' | 'plan' | 'progress' | 'settings') => void;
}

export default function ProgressScreen({ onNavigate }: ProgressScreenProps) {
  const monthlyProgress = [
    { month: 'January', days: 12, total: 31 },
    { month: 'December', days: 28, total: 31 },
    { month: 'November', days: 30, total: 30 },
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
        <h2 className="text-[#4a5548]">Your Progress</h2>
        <p className="text-sm text-[#8a9488] mt-1">Keep building your habit</p>
      </div>

      {/* Progress Content */}
      <div className="flex-1 px-6 py-6 overflow-auto">
        {/* Overall Progress */}
        <div className="bg-white/70 backdrop-blur-sm rounded-3xl p-6 shadow-sm border border-[#e8e4db]/50 mb-6">
          <div className="text-center mb-6">
            <div className="inline-flex items-center justify-center w-32 h-32 rounded-full bg-gradient-to-br from-[#a5b4a1]/20 to-[#d4a574]/20 mb-4 relative">
              <svg className="absolute inset-0 w-full h-full -rotate-90" viewBox="0 0 100 100">
                <circle
                  cx="50"
                  cy="50"
                  r="45"
                  fill="none"
                  stroke="#e8e4db"
                  strokeWidth="8"
                />
                <circle
                  cx="50"
                  cy="50"
                  r="45"
                  fill="none"
                  stroke="#a5b4a1"
                  strokeWidth="8"
                  strokeDasharray={`${3 * 2.83} ${100 * 2.83}`}
                  strokeLinecap="round"
                />
              </svg>
              <div className="relative">
                <p className="text-[#4a5548] mb-1">3%</p>
                <p className="text-xs text-[#8a9488]">Complete</p>
              </div>
            </div>
          </div>

          <div className="space-y-4">
            <div className="flex items-center justify-between p-4 bg-[#faf8f5] rounded-2xl">
              <div className="flex items-center gap-3">
                <div className="w-10 h-10 rounded-full bg-[#a5b4a1]/20 flex items-center justify-center">
                  <BookOpen className="w-5 h-5 text-[#6b7b68]" />
                </div>
                <div>
                  <p className="text-sm text-[#8a9488]">Days completed</p>
                  <p className="text-[#4a5548]">12 days</p>
                </div>
              </div>
            </div>

            <div className="flex items-center justify-between p-4 bg-[#faf8f5] rounded-2xl">
              <div className="flex items-center gap-3">
                <div className="w-10 h-10 rounded-full bg-[#d4a574]/20 flex items-center justify-center">
                  <TrendingUp className="w-5 h-5 text-[#c09858]" />
                </div>
                <div>
                  <p className="text-sm text-[#8a9488]">Current streak</p>
                  <p className="text-[#4a5548]">12 days</p>
                </div>
              </div>
            </div>

            <div className="flex items-center justify-between p-4 bg-[#faf8f5] rounded-2xl">
              <div className="flex items-center gap-3">
                <div className="w-10 h-10 rounded-full bg-[#a5b4a1]/20 flex items-center justify-center">
                  <Calendar className="w-5 h-5 text-[#6b7b68]" />
                </div>
                <div>
                  <p className="text-sm text-[#8a9488]">Days remaining</p>
                  <p className="text-[#4a5548]">353 days</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Monthly Overview */}
        <div className="bg-white/70 backdrop-blur-sm rounded-3xl p-6 shadow-sm border border-[#e8e4db]/50 mb-6">
          <h3 className="text-[#4a5548] mb-4">Monthly Overview</h3>
          <div className="space-y-3">
            {monthlyProgress.map((month, index) => (
              <div key={month.month}>
                <div className="flex justify-between items-center mb-2">
                  <span className="text-sm text-[#6b7b68]">{month.month}</span>
                  <span className="text-sm text-[#8a9488]">
                    {month.days} / {month.total} days
                  </span>
                </div>
                <div className="h-2 bg-[#e8e4db] rounded-full overflow-hidden">
                  <div
                    className="h-full bg-[#a5b4a1] rounded-full transition-all"
                    style={{ width: `${(month.days / month.total) * 100}%` }}
                  ></div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Encouragement */}
        <div className="bg-gradient-to-br from-[#a5b4a1]/10 to-[#d4a574]/10 rounded-3xl p-6 border border-[#a5b4a1]/20 text-center">
          <p className="text-[#6b7b68] leading-relaxed">
            "Your word is a lamp to my feet and a light to my path."
          </p>
          <p className="text-sm text-[#8a9488] mt-2">Psalm 119:105</p>
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
