import { BookOpen, Calendar, TrendingUp, Settings, Check } from 'lucide-react';
import { useState } from 'react';

interface HomeScreenProps {
  selectedPlan: string;
  onNavigate: (screen: 'home' | 'plan' | 'progress' | 'settings') => void;
}

export default function HomeScreen({ selectedPlan, onNavigate }: HomeScreenProps) {
  const [isRead, setIsRead] = useState(false);
  
  const today = new Date().toLocaleDateString('en-US', { 
    weekday: 'long', 
    month: 'long', 
    day: 'numeric' 
  });

  return (
    <div className="h-full flex flex-col bg-gradient-to-b from-[#faf8f5] to-[#f5f1e8]">
      {/* Header */}
      <div className="px-6 pt-14 pb-6">
        <h2 className="text-[#4a5548] mb-2">Bible Reading Tracker</h2>
        <p className="text-sm text-[#8a9488]">{today}</p>
      </div>

      {/* Main Content */}
      <div className="flex-1 px-6 pb-6 overflow-auto">
        {/* Today's Reading Card */}
        <div className="bg-white/70 backdrop-blur-sm rounded-3xl p-6 shadow-sm border border-[#e8e4db]/50 mb-6">
          <div className="flex items-center gap-2 mb-4 text-[#a5b4a1]">
            <BookOpen className="w-5 h-5" />
            <span className="text-sm">Today's Reading</span>
          </div>
          
          <div className="mb-6">
            <p className="text-sm text-[#8a9488] mb-2">Day 12</p>
            <h1 className="text-[#4a5548]">Genesis 4–6</h1>
          </div>

          <button
            onClick={() => setIsRead(!isRead)}
            className={`w-full py-4 rounded-full transition-all ${
              isRead 
                ? 'bg-[#a5b4a1]/20 text-[#6b7b68] border-2 border-[#a5b4a1]' 
                : 'bg-[#a5b4a1] text-white shadow-md hover:bg-[#94a390]'
            }`}
          >
            <span className="flex items-center justify-center gap-2">
              {isRead ? (
                <>
                  <Check className="w-5 h-5" />
                  Completed
                </>
              ) : (
                'Mark as Read'
              )}
            </span>
          </button>
        </div>

        {/* Progress Overview */}
        <div className="bg-white/70 backdrop-blur-sm rounded-3xl p-6 shadow-sm border border-[#e8e4db]/50 mb-6">
          <h3 className="text-[#4a5548] mb-4">Your Progress</h3>
          
          <div className="mb-4">
            <div className="flex justify-between items-center mb-2">
              <span className="text-sm text-[#8a9488]">Overall completion</span>
              <span className="text-sm text-[#6b7b68]">3%</span>
            </div>
            <div className="h-2 bg-[#e8e4db] rounded-full overflow-hidden">
              <div className="h-full bg-[#a5b4a1] rounded-full" style={{ width: '3%' }}></div>
            </div>
          </div>

          <div className="flex items-center gap-2 text-[#8a9488] text-sm">
            <div className="w-2 h-2 bg-[#d4a574] rounded-full"></div>
            <span>12 days reading streak</span>
          </div>
        </div>

        {/* Quick Actions */}
        <div className="grid grid-cols-3 gap-3 mb-6">
          <button
            onClick={() => onNavigate('plan')}
            className="bg-white/60 backdrop-blur-sm p-4 rounded-2xl shadow-sm border border-[#e8e4db]/50 flex flex-col items-center gap-2 hover:scale-105 transition-transform"
          >
            <Calendar className="w-6 h-6 text-[#a5b4a1]" />
            <span className="text-xs text-[#6b7b68]">Plan</span>
          </button>
          
          <button
            onClick={() => onNavigate('progress')}
            className="bg-white/60 backdrop-blur-sm p-4 rounded-2xl shadow-sm border border-[#e8e4db]/50 flex flex-col items-center gap-2 hover:scale-105 transition-transform"
          >
            <TrendingUp className="w-6 h-6 text-[#a5b4a1]" />
            <span className="text-xs text-[#6b7b68]">Progress</span>
          </button>
          
          <button
            onClick={() => onNavigate('settings')}
            className="bg-white/60 backdrop-blur-sm p-4 rounded-2xl shadow-sm border border-[#e8e4db]/50 flex flex-col items-center gap-2 hover:scale-105 transition-transform"
          >
            <Settings className="w-6 h-6 text-[#a5b4a1]" />
            <span className="text-xs text-[#6b7b68]">Settings</span>
          </button>
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
