import { ChevronLeft, ChevronRight, BookOpen, Bell, Crown } from 'lucide-react';

interface SettingsScreenProps {
  onNavigate: (screen: 'home' | 'plan' | 'progress' | 'settings') => void;
}

export default function SettingsScreen({ onNavigate }: SettingsScreenProps) {
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
        <h2 className="text-[#4a5548]">Settings</h2>
      </div>

      {/* Settings Content */}
      <div className="flex-1 px-6 py-6 overflow-auto">
        {/* Premium Card */}
        <div className="bg-gradient-to-br from-[#d4a574]/20 to-[#a5b4a1]/20 rounded-3xl p-6 shadow-sm border border-[#d4a574]/30 mb-6">
          <div className="flex items-start gap-4 mb-4">
            <div className="w-12 h-12 rounded-full bg-white/60 flex items-center justify-center flex-shrink-0">
              <Crown className="w-6 h-6 text-[#c09858]" />
            </div>
            <div className="flex-1">
              <h3 className="text-[#4a5548] mb-1">Premium</h3>
              <p className="text-sm text-[#8a9488] leading-relaxed">
                Remove ads for a distraction-free, peaceful reading experience
              </p>
            </div>
          </div>
          <button className="w-full bg-white/80 text-[#6b7b68] py-3 rounded-full shadow-sm hover:bg-white transition-colors">
            Upgrade to Premium
          </button>
        </div>

        {/* Reading Plan Settings */}
        <div className="bg-white/70 backdrop-blur-sm rounded-3xl p-6 shadow-sm border border-[#e8e4db]/50 mb-4">
          <h3 className="text-[#4a5548] mb-4">Reading Plan</h3>
          
          <button className="w-full flex items-center justify-between p-4 bg-[#faf8f5] rounded-2xl hover:bg-[#f5f1e8] transition-colors">
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 rounded-full bg-[#a5b4a1]/20 flex items-center justify-center">
                <BookOpen className="w-5 h-5 text-[#6b7b68]" />
              </div>
              <div className="text-left">
                <p className="text-sm text-[#8a9488]">Current plan</p>
                <p className="text-[#4a5548]">1 Year Plan</p>
              </div>
            </div>
            <ChevronRight className="w-5 h-5 text-[#c5c9c3]" />
          </button>
        </div>

        {/* Reminder Settings */}
        <div className="bg-white/70 backdrop-blur-sm rounded-3xl p-6 shadow-sm border border-[#e8e4db]/50 mb-4">
          <h3 className="text-[#4a5548] mb-4">Reminders</h3>
          
          <button className="w-full flex items-center justify-between p-4 bg-[#faf8f5] rounded-2xl hover:bg-[#f5f1e8] transition-colors">
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 rounded-full bg-[#a5b4a1]/20 flex items-center justify-center">
                <Bell className="w-5 h-5 text-[#6b7b68]" />
              </div>
              <div className="text-left">
                <p className="text-sm text-[#8a9488]">Daily reminder</p>
                <p className="text-[#4a5548]">8:00 AM</p>
              </div>
            </div>
            <ChevronRight className="w-5 h-5 text-[#c5c9c3]" />
          </button>
        </div>

        {/* Other Settings */}
        <div className="bg-white/70 backdrop-blur-sm rounded-3xl p-6 shadow-sm border border-[#e8e4db]/50 mb-6">
          <h3 className="text-[#4a5548] mb-4">Other</h3>
          
          <div className="space-y-2">
            <button className="w-full flex items-center justify-between p-4 bg-[#faf8f5] rounded-2xl hover:bg-[#f5f1e8] transition-colors text-left">
              <span className="text-[#6b7b68]">About</span>
              <ChevronRight className="w-5 h-5 text-[#c5c9c3]" />
            </button>
            
            <button className="w-full flex items-center justify-between p-4 bg-[#faf8f5] rounded-2xl hover:bg-[#f5f1e8] transition-colors text-left">
              <span className="text-[#6b7b68]">Privacy Policy</span>
              <ChevronRight className="w-5 h-5 text-[#c5c9c3]" />
            </button>
            
            <button className="w-full flex items-center justify-between p-4 bg-[#faf8f5] rounded-2xl hover:bg-[#f5f1e8] transition-colors text-left">
              <span className="text-[#6b7b68]">Share with Friends</span>
              <ChevronRight className="w-5 h-5 text-[#c5c9c3]" />
            </button>
          </div>
        </div>

        {/* App Version */}
        <div className="text-center pb-6">
          <p className="text-xs text-[#c5c9c3]">Bible Reading Tracker v1.0.0</p>
        </div>
      </div>
    </div>
  );
}
