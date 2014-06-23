require 'rubygems'
require 'nokogiri' 
require 'open-uri'
require 'mechanize'
require 'rubyXL'

agent = Mechanize.new

workbook = RubyXL::Parser.parse("EEC PARTS.xlsx")
worksheet = workbook[0]

items = 500

def progress(currentItem, maxItems, percentage)
	hc = (currentItem + 1)/(items) # hc refers to "how close to done"
	if (hc - percentage).abs < 0.0001
		# it's hit
		puts (100*percentage).to_s + "% complete"
	end
end

begin
	file = File.open("results.xls", "w")
	for i in 0..items # excel file contains 6791 product numbers
		_part = "site:eecontrols.com filetype:pdf " + worksheet[i][0].value.to_s # doesn't get results?
		#_part = worksheet[i][0].value.to_s
		
		progress(i, items, 0.25)
		progress(i, items, 0.50)
		progress(i, items, 0.75)
		progress(i, items, 0.90)

		agent.get('http://www.google.com') do |page|
			search_result = page.form_with(:action => '/search') do |search|
				search.q = _part
			end.submit
			
			sleep (Random.rand(2) + 1) # prevent 503 errors
			
			page = Nokogiri::HTML(open(search_result.uri.to_s))
			results = page.css('div h3 a')
			
			sleep (Random.rand(2) + 1) # prevent 503 errors

			if results.length < 1
				# can't find a pdf for the part on the official company's website
				file.write("\n")
			else
				if (results[0].to_s).split('href="').first != "<a "
					# not sure what this bug is
					# puts _part
					# puts results[0].to_s.split('href="').first
					file.write("\n")
				else
					link = "www.google.com" + (results[0].to_s).gsub('<a href="', "")
					link = link.split('">').first
					link = "http://" + link # now link looks like: http://www.google.com/url?q=http://www.eecontrols.com/documents/Page170.pdf&amp;sa=U&amp;ei=MRqoU6iCDuTK8wG4wIGACg&amp;ved=0CBQQFjAA&amp;usg=AFQjCNG6JKe0-JvfV-PK86YcF4tvhusKyQ

					fetch = Nokogiri::HTML(open(link.to_s))
					actualLink = fetch.css('div a')[0].to_s
					
					actualLink = actualLink.gsub('<a href="', "")
					actualLink = actualLink.split('">').first
					
					# awesome, now actualLink stores: http://www.eecontrols.com/documents/Page170.pdf
					file.write(actualLink + "\n")
				end
			end
		end
		
	end 
	rescue IOError => e
		#some error occur, directory not writable etc.
	ensure
		file.close unless file == nil
end


=begin
root = TkRoot.new { title "Hello, World!" }
TkLabel.new(root) do
   text 'Hello, World!'
   #pack { padx 15 ; pady 15; side 'left' }
end
Tk.mainloop
=end


