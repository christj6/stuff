require 'rubygems'
require 'nokogiri' 
require 'open-uri'
require 'mechanize'
require 'rubyXL'

agent = Mechanize.new

workbook = RubyXL::Parser.parse("EEC PARTS.xlsx")
worksheet = workbook[0]

begin
	file = File.open("results.xls", "w")
	for i in 0..50 # excel file contains thousands of product numbers -- change this 5 later
		_part = "site:eecontrols.com filetype:pdf " + worksheet[i][0].value.to_s # doesn't get results?
		#_part = worksheet[i][0].value.to_s
		
		agent.get('http://www.google.com') do |page|
			search_result = page.form_with(:action => '/search') do |search|
				search.q = _part
			end.submit
			
			page = Nokogiri::HTML(open(search_result.uri.to_s))
			results = page.css('div h3 a')

			if results.length < 1
				# can't find a pdf for the part on the official company's website
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


