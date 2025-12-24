import { BookOpen, Sparkles } from 'lucide-react';

interface WelcomeScreenProps {
  onStartReading: (plan: string) => void;
}

export default function WelcomeScreen({ onStartReading }: WelcomeScreenProps) {
  const plans = [
    { id: '1year', title: '1 Year Plan', description: 'Read through the entire Bible in one year' },
    { id: '90days', title: '90 Days', description: 'Complete the Bible in three months' },
    { id: 'newtestament', title: 'New Testament', description: 'Focus on the life and teachings of Jesus' },
  ];

  return (
    <div className="h-full flex flex-col bg-gradient-to-b from-[#faf8f5] to-[#f5f1e8] px-6 py-12">
      {/* Logo and Title */}
      <div className="flex-1 flex flex-col items-center justify-center text-center mb-8">
        <div className="mb-6 relative">
          <div className="absolute inset-0 bg-[#a5b4a1]/20 blur-3xl rounded-full"></div>
          <div className="relative bg-[#a5b4a1]/30 p-6 rounded-full">
            <BookOpen className="w-16 h-16 text-[#6b7b68]" strokeWidth={1.5} />
          </div>
        </div>
        
        <h1 className="text-[#4a5548] mb-3 px-4">
          A Quiet Journey Through the Bible
        </h1>
        
        <p className="text-[#8a9488] max-w-[280px] leading-relaxed">
          Build a peaceful, consistent habit of daily Scripture reading
        </p>

        <div className="mt-6 flex items-center gap-2 text-[#a5b4a1]">
          <Sparkles className="w-4 h-4" />
          <span className="text-sm">Finding peace in God's Word</span>
          <Sparkles className="w-4 h-4" />
        </div>
      </div>

      {/* Reading Plans */}
      <div className="space-y-3 mb-8">
        <p className="text-sm text-[#8a9488] text-center mb-4">Choose your reading plan</p>
        {plans.map((plan) => (
          <button
            key={plan.id}
            onClick={() => onStartReading(plan.id)}
            className="w-full bg-white/60 backdrop-blur-sm p-5 rounded-3xl shadow-sm hover:shadow-md transition-all hover:scale-[1.02] border border-[#e8e4db]/50 text-left"
          >
            <h3 className="text-[#4a5548] mb-1">{plan.title}</h3>
            <p className="text-sm text-[#8a9488]">{plan.description}</p>
          </button>
        ))}
      </div>

      {/* Start Button */}
      <button
        onClick={() => onStartReading('1year')}
        className="w-full bg-[#a5b4a1] text-white py-4 rounded-full shadow-lg hover:bg-[#94a390] transition-colors"
      >
        Start Reading
      </button>
    </div>
  );
}
